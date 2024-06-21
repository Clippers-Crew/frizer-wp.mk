package mk.frizer.service;

import mk.frizer.model.Salon;
import mk.frizer.model.SearchQuery;

import java.util.List;

public interface SalonSortService {

        List<Salon> sortSalons(String sortingMethod, SearchQuery retrievedQuery, String userLocation);
        //    List<Salon> sortSalonsByDistance(List<Salon> salons, String userLocation);
        List<Salon> sortSalonsByLocation(List<Salon> salons);
        List<Salon> sortSalonsByRating(List<Salon> salons);

}
