package mk.frizer.web.controller;

import mk.frizer.model.Salon;
import mk.frizer.service.BaseUserService;
import mk.frizer.service.CityService;
import mk.frizer.service.ReviewService;
import mk.frizer.service.SalonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping({"/home", "/"})
public class HomeController {
    private final SalonService salonService;
    private final ReviewService reviewService;
    private final CityService cityService;

    public HomeController(SalonService salonService, ReviewService reviewService, CityService cityService) {
        this.salonService = salonService;
        this.reviewService = reviewService;
        this.cityService = cityService;
    }

    @GetMapping
    public String getHomePage(Model model) {

        List<Salon> salons = salonService.getSalons();
        model.addAttribute("salons",salons);
        model.addAttribute("salonRatings", reviewService.getStatisticsForSalon(salons));
        model.addAttribute("cities", cityService.getCities());
        model.addAttribute("bodyContent", "home");
        return "master-template";
    }

//    @GetMapping("/access_denied")
//    public String getAccessDeniedPage(Model model) {
//        model.addAttribute("bodyContent","access-denied");
//        return "master-template";
//    }
}
