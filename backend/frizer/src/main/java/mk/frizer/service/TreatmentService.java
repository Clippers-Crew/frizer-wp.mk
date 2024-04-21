package mk.frizer.service;

import mk.frizer.model.Treatment;
import java.util.List;
import java.util.Optional;

public interface TreatmentService{
    List<Treatment> getTreatments();
    Optional<Treatment> getTreatmentById(Long id);
    Optional<Treatment> createTreatment(String name);
    Optional<Treatment> updateTreatment(Long id, String name, List<Long> salonTreatmentIds);
    Optional<Treatment> deleteTreatmentById(Long id);
}
