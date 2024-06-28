package mk.frizer.web.controller;

import jakarta.servlet.http.HttpSession;
import mk.frizer.model.Salon;
import mk.frizer.model.SearchQuery;
import mk.frizer.service.SalonService;
import mk.frizer.service.SalonSortService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {
    private final SalonService salonService;

    private final SalonSortService salonSortService;

    public SearchController(SalonService salonService, SalonSortService salonSortService) {
        this.salonService = salonService;
        this.salonSortService = salonSortService;
    }

//    @GetMapping("/map")
//    public String showSalonsMap(Model model, HttpSession session){
//        List<String> salons = salonService.getSalonsAsString(salonService.getSalons());
//        System.out.println("Salons: " + salons);
//        model.addAttribute("salons", salons);
//        setDefaultSearchParameters(model ,session);
//        model.addAttribute("bodyContent","map");
//        return "master-template";
//    }
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
