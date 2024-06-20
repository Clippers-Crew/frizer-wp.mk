package mk.frizer.web.controller;

import mk.frizer.model.Salon;
import mk.frizer.service.BaseUserService;
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
    public HomeController(SalonService salonService) {
        this.salonService = salonService;
    }

    @GetMapping
    public String getHomePage(Model model) {

        List<Salon> salons = salonService.getSalons();
        model.addAttribute("salons",salons);
        model.addAttribute("bodyContent", "home");
        return "master-template";
    }

//    @GetMapping("/access_denied")
//    public String getAccessDeniedPage(Model model) {
//        model.addAttribute("bodyContent","access-denied");
//        return "master-template";
//    }
}
