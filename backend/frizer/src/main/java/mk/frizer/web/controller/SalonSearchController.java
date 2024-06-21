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
import java.util.stream.Collectors;

@Controller
@RequestMapping("/search")
public class SalonSearchController {
    private final SalonService salonService;

    private final SalonSortService salonSortService;

    public SalonSearchController(SalonService salonService, SalonSortService salonSortService) {
        this.salonService = salonService;
        this.salonSortService = salonSortService;
    }

    private void setDefaultSearchParameters(Model model, HttpSession session){
        SearchQuery searchQuery = new SearchQuery("", (float)0, (float)300, "Цела Македонија", new ArrayList<>());
        setSearchAttributes(model, searchQuery);
        addSearchQueryAttribute(session, searchQuery);
    }

    @GetMapping()
    public String getResultsMapping(Model model, HttpSession session,  @ModelAttribute("salons") ArrayList<Salon> salons) {
        if(salons != null && !salons.isEmpty()){
            model.addAttribute("salons", salons);
        }
        else{
            model.addAttribute("salons", salonService.getSalons());
            setDefaultSearchParameters(model, session);
        }
        model.addAttribute("salons",salons);
        model.addAttribute("bodyContent","filter");
        return "master-template";
    }
    @PostMapping()
    public String postResultsMapping(@RequestParam(name="name", required = false) String name,
                                     @RequestParam(name = "rating", required = false) Float rating,
                                     @RequestParam(name = "distance", required = false) Float distance,
                                     @RequestParam(name = "location", required = false) String city,
                                     @RequestParam(name = "sort", required = false) String sortingMethod,
                                     Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        SearchQuery retrievedQuery = (SearchQuery) session.getAttribute("searchQuery");

        String userLocation = (String) session.getAttribute("userGeolocation");

        if(retrievedQuery == null && name == null && city == null && rating == null && distance == null){
            List<Salon> salons = salonService.getSalons();
            setSearchAttributes(model, retrievedQuery);
            redirectAttributes.addFlashAttribute("salons", null);
            model.addAttribute("bodyContent","filter");
            return "master-template";
        }
        // if everything is null, set to default values
        if(name == null) {
            name = "";
        }
        if(rating == null){
            rating = (float)0.0;
        }
        if(distance == null){
            distance = (float)300;
        }
        if(city == null){
            city = "Цела Македонија";
        }

        List<Salon> filteredSalons = salonService.filteredSalons(name, city, distance, rating);

        SearchQuery searchQuery = new SearchQuery(name, rating,distance, city,filteredSalons);
        setSearchAttributes(model, searchQuery);
        addSearchQueryAttribute(session, searchQuery);
        redirectAttributes.addFlashAttribute("salons", filteredSalons);
        model.addAttribute("count",filteredSalons.size());
        model.addAttribute("bodyContent","filter");
        return "master-template";
    }
    private void addSearchQueryAttribute(HttpSession session, SearchQuery searchQuery){
        session.setAttribute("searchQuery", searchQuery);
    }
    private void setSearchAttributes(Model model, SearchQuery searchQuery) {
        model.addAttribute("searchName", searchQuery.getName());
        model.addAttribute("searchRating", searchQuery.getRating());
        model.addAttribute("searchDistance", searchQuery.getDistance());
        model.addAttribute("searchCity", searchQuery.getCity());
        model.addAttribute("salons", searchQuery.getSalons());
        model.addAttribute("count",searchQuery.getSalons().size());

    }

    @GetMapping("/map")
    public String showSalonsMap(Model model, HttpSession session){
        List<String> salons = salonService.getSalonsAsString(salonService.getSalons());
        System.out.println("Salons: " + salons);
        model.addAttribute("salons", salons);
        setDefaultSearchParameters(model ,session);
        model.addAttribute("bodyContent","map");
        return "master-template";
    }
    @PostMapping("/map")
    public String showFilteredSalonsMap(HttpSession session, Model model){

        SearchQuery retrievedQuery = (SearchQuery) session.getAttribute("searchQuery");

        if(retrievedQuery == null){
            List<String> salons = salonService.getSalonsAsString(salonService.getSalons());
            model.addAttribute("salons", salons);
        }
        else{

            List<String> salons = salonService.getSalonsAsString(retrievedQuery.getSalons());
            model.addAttribute("salons",salons);
        }
        model.addAttribute("bodyContent","map");
        return "master-template";
    }





}
