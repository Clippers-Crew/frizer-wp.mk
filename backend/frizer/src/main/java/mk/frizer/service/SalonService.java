package mk.frizer.service;

import mk.frizer.model.BusinessOwner;
import mk.frizer.model.Review;
import mk.frizer.model.Salon;

import java.util.List;
import java.util.Optional;

public interface SalonService {
    List<Salon> getSalons();
    Optional<Salon> getSalonById(Long id);
    Optional<Salon> createSalon(String name, String description, String location, String phoneNumber, Long businessOwnerId);
    Optional<Salon> updateSalon(Long id, String name, String description, String location, String phoneNumber, List<Long> employeeIds, List<Long> salonTreatmentIds, Long businessOwnerId);
    Optional<Salon> deleteSalonById(Long id);
}
