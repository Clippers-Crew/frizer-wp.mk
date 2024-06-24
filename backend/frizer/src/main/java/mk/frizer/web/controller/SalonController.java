package mk.frizer.web.controller;

import jakarta.servlet.http.HttpSession;
import mk.frizer.model.*;
import mk.frizer.model.exceptions.SalonNotFoundException;
import mk.frizer.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/salons")
public class SalonController {
    private final SalonService salonService;
    private final TagService tagService;
    private final ReviewService reviewService;
    private final EmployeeService employeeService;

    private final TreatmentService treatmentService;

    public SalonController(SalonService salonService, TagService tagService, ReviewService reviewService, EmployeeService employeeService, TreatmentService treatmentService) {
        this.salonService = salonService;
        this.tagService = tagService;
        this.reviewService = reviewService;
        this.employeeService = employeeService;
        this.treatmentService = treatmentService;
    }

    @GetMapping("/{id}")
    public String getHomePage(@PathVariable Long id, Model model) {

        Salon salon = salonService.getSalonById(id).orElseThrow(SalonNotFoundException::new);
        List<Tag> tags = tagService.getTagsForSalon(id);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        List<Employee> employees = employeeService.getEmployeesForSalon(id);
        List<Review> reviews = reviewService.getReviewsForEmployees(employees);
        List<Treatment> treatments = treatmentService.getTreatmentsForSalon(id);
        Map<Long, ReviewStats> employeeMap = reviewService.getStatisticsForEmployee(employees);
        model.addAttribute("employeeMap", employeeMap);
        model.addAttribute("treatments", treatments);
        model.addAttribute("formatter", formatter);
        model.addAttribute("salon", salon);
        model.addAttribute("employees", employees);
        model.addAttribute("reviews", reviews);
        model.addAttribute("tags", tags);
        model.addAttribute("salonAsString", salonService.getSalonAsString(salon));
        model.addAttribute("bodyContent", "salon");
        return "master-template";
    }

    @GetMapping("/search")
    public String searchSalons(@RequestParam(required = false) String name, @RequestParam(required = false) String city, @RequestParam(required = false) String distance, @RequestParam(required = false) String rating, Model model) {
        name = (name != null && name.isEmpty()) ? null : name;
        city = (city != null && city.isEmpty()) ? null : city;
        distance = (distance != null && distance.isEmpty()) ? null : distance;
        rating = (rating != null && rating.isEmpty()) ? null : rating;

        List<Salon> salons = salonService.filterSalons(name, city, distance, rating);
        model.addAttribute("salons", salons);
        model.addAttribute("bodyContent", "search");
        return "master-template";
    }

   }