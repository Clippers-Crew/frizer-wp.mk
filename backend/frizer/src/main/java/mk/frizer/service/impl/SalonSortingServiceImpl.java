package mk.frizer.service.impl;

import mk.frizer.model.Salon;
import mk.frizer.model.SearchQuery;
import mk.frizer.service.SalonService;
import mk.frizer.service.SalonSortService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalonSortingServiceImpl implements SalonSortService {
    //    @Override
//    public List<Salon> sortSalonsByDistance(List<Salon> salons, String userLocation){
//        salons.sort(Comparator.comparingDouble(winery -> winery.);
//        return salons;
//    }
    @Override
    public List<Salon> sortSalonsByRating(List<Salon> salons) {
        return salons.stream()
                .sorted(Comparator.comparing(Salon::getRating, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Salon> sortSalonsByLocation(List<Salon> salons) {
        return salons.stream()
                .sorted(Comparator.comparing(Salon::getLocation, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Salon> sortSalons(String sortingMethod, SearchQuery retrievedQuery, String userLocation) {
        List<Salon> current = retrievedQuery.getSalons();
        if (sortingMethod.equals("city")) {
            current = sortSalonsByLocation(current);
        } else if (sortingMethod.equals("rating")) {
            current = sortSalonsByRating(current);
        } else if (sortingMethod.equals("name")) {
            current = sortSalonsByName(current);
        }
        retrievedQuery.setSalons(current);
        return current;
    }

    private List<Salon> sortSalonsByName(List<Salon> salons) {
        return salons.stream()
                .sorted(Comparator.comparing(Salon::getName, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }
}
