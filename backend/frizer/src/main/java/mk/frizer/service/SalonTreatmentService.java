package mk.frizer.service;

import mk.frizer.model.Salon;
import mk.frizer.model.SalonTreatment;

import java.util.List;
import java.util.Optional;

public interface SalonTreatmentService {

    List<SalonTreatment> getSalonTreatments();
    Optional<SalonTreatment> getSalonTreatmentById(Long id);
    Optional<SalonTreatment> createSalonTreatment(Long salonId,Long treatmentId,Double price);
    Optional<SalonTreatment> updateSalonTreatment(Long id, Long salonId, Long treatmentId, Double price);
    Optional<SalonTreatment> deleteSalonTreatmentById(Long id);
}
