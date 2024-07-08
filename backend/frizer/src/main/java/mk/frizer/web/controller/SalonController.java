package mk.frizer.web.controller;

import jakarta.servlet.http.HttpSession;
import mk.frizer.model.*;
import mk.frizer.model.exceptions.SalonNotFoundException;
import mk.frizer.service.*;
import mk.frizer.service.impl.CityServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;

@Controller
@RequestMapping("/salons")
public class SalonController {
    private final SalonService salonService;
    private final ReviewService reviewService;
    private final CustomerService customerService;
    private final BaseUserService baseUserService;
    private final CityService cityService;

    public SalonController(SalonService salonService, ReviewService reviewService, CustomerService customerService, BaseUserService baseUserService, CityService cityService) {
        this.salonService = salonService;
        this.reviewService = reviewService;
        this.customerService = customerService;
        this.baseUserService = baseUserService;
        this.cityService = cityService;
    }

    @GetMapping("/{id}")
    public String salonDetailsPage(@PathVariable Long id, Model model, Principal principal) {
        Salon salon = salonService.getSalonById(id).orElseThrow(SalonNotFoundException::new);
        List<Tag> tags = salon.getTags();
        List<Treatment> treatments = salon.getSalonTreatments();
        List<Employee> employees = salon.getEmployees();
        List<Review> reviews = reviewService.getReviewsForEmployees(employees);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        Map<Long, ReviewStats> employeeMap = reviewService.getStatisticsForEmployee(employees);

        ReviewStats salonStats = employeeMap.values().stream()
                .collect(Collector.of(
                        () -> new double[3],
                        (a, rs) -> {
                            a[0] += rs.getRating();
                            a[1] += rs.getNumberOfReviews();
                            a[2]++;
                        },
                        (a, b) -> { // combiner
                            a[0] += b[0];
                            a[1] += b[1];
                            a[2] += b[2];
                            return a;
                        },
                        a -> new ReviewStats(a[0] / (a[2] == 0 ? 1 : a[2]), (int) a[1]) // finisher
                ));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean canAddTreatment = false;
        boolean canAddEmployee = false;

        if (authentication != null && authentication.isAuthenticated() && principal != null) {
            canAddTreatment  = salonService.isUserAuthorizedToAddTreatment(id, principal.getName());
            canAddEmployee = salonService.isUserAuthorizedToAddSalon(id, principal.getName());

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            model.addAttribute("employees", employees.stream()
                    .filter(e -> !e.getBaseUser().getEmail().equals(userDetails.getUsername())).toList());
        }
        else{
            model.addAttribute("employees", employees);
        }

        model.addAttribute("canAddTreatment", canAddTreatment);
        model.addAttribute("canAddEmployee", canAddEmployee);
        model.addAttribute("employeeMap", employeeMap);
        model.addAttribute("treatments", treatments);
        model.addAttribute("formatter", formatter);
        model.addAttribute("salon", salon);
        model.addAttribute("salonStats", salonStats);
        model.addAttribute("reviews", reviews);
        model.addAttribute("tags", tags);
        model.addAttribute("salonAsString", salonService.getSalonAsString(salon));
        model.addAttribute("customers", customerService.getCustomers());

        model.addAttribute("baseUsers", baseUserService.getBaseUsers().stream()
                .filter(e -> !salon.getOwner().getBaseUser().equals(e) &&
                        salon.getEmployees().stream().map(Employee::getBaseUser).noneMatch(emp -> emp.equals(e))));

        model.addAttribute("bodyContent", "salon");
        return "master-template";
    }

    private List<Salon> setSearchAttributes(String name, Float rating, Float distance, String city, String sortingMethod, Model model, HttpSession session) {
        model.addAttribute("searchName", name);
        model.addAttribute("searchRating", rating);
        model.addAttribute("searchDistance", distance);
        model.addAttribute("searchCity", city);

        if (name == null) {
            name = "";
        }
        if (rating == null || rating < 0) {
            rating = (float) 0.0;
        }
        if (distance == null || distance < 0) {
            distance = (float) 300;
        }
        if (city == null || city.isEmpty()) {
            city = "Цела Македонија";
        }

        // Saved in another controller on the start
        String userLocation = (String) session.getAttribute("userGeolocation");
        List<Salon> filteredSalons = salonService.filterSalons(name, city, distance, rating, userLocation);

        model.addAttribute("salons", filteredSalons);
        model.addAttribute("count", filteredSalons.size());
        return filteredSalons;
    }

    @GetMapping()
    public String getSalons(@RequestParam(name = "name", required = false) String name,
                            @RequestParam(name = "rating", required = false) Float rating,
                            @RequestParam(name = "distance", required = false) Float distance,
                            @RequestParam(name = "city", required = false) String city,
                            @RequestParam(name = "sort", required = false) String sortingMethod,
                            Model model, HttpSession session) {
        setSearchAttributes(name, rating, distance, city, sortingMethod, model, session);
        model.addAttribute("cities", cityService.getCities());
        model.addAttribute("salonRatings", reviewService.getStatisticsForSalon(salonService.getSalons()));
        model.addAttribute("bodyContent", "salons-list");
        return "master-template";
    }


    @GetMapping("/map")
    public String showSalonsMap(@RequestParam(name = "name", required = false) String name,
                                @RequestParam(name = "rating", required = false) Float rating,
                                @RequestParam(name = "distance", required = false) Float distance,
                                @RequestParam(name = "city", required = false) String city,
                                @RequestParam(name = "sort", required = false) String sortingMethod,
                                Model model, HttpSession session){
        List<Salon> filteredSalons = setSearchAttributes(name, rating, distance, city, sortingMethod, model, session);

        List<String> salons = salonService.getSalonsAsString(filteredSalons);
        model.addAttribute("salonsAsString", salons);

        model.addAttribute("cities", cityService.getCities());
        model.addAttribute("bodyContent", "salons-map");
        return "master-template";
    }


//
//    @PostMapping("/map")
//    public String showFilteredSalonsMap(HttpSession session, Model model){
//
//        SearchQuery retrievedQuery = (SearchQuery) session.getAttribute("searchQuery");
//
//        if(retrievedQuery == null){
//            List<String> salons = salonService.getSalonsAsString(salonService.getSalons());
//            model.addAttribute("salons", salons);
//        }
//        else{
//
//            List<String> salons = salonService.getSalonsAsString(retrievedQuery.getSalons());
//            model.addAttribute("salons",salons);
//        }
//        model.addAttribute("bodyContent","map");
//        return "master-template";
//    }


}