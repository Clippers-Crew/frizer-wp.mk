package mk.frizer.service.impl;

import mk.frizer.model.SalonTreatment;
import mk.frizer.model.Treatment;
import mk.frizer.model.exceptions.TreatmentNotFoundException;
import mk.frizer.repository.SalonTreatmentRepository;
import mk.frizer.repository.TreatmentRepository;
import mk.frizer.service.TreatmentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TreatmentServiceImpl implements TreatmentService {
    private final TreatmentRepository treatmentRepository;
    private final SalonTreatmentRepository salonTreatmentRepository;

    public TreatmentServiceImpl(TreatmentRepository treatmentRepository, SalonTreatmentRepository salonTreatmentRepository) {
        this.treatmentRepository = treatmentRepository;
        this.salonTreatmentRepository = salonTreatmentRepository;
    }

    @Override
    public List<Treatment> getTreatments() {
        return treatmentRepository.findAll();
    }

    @Override
    public Optional<Treatment> getTreatmentById(Long id) {
        Treatment treatment = treatmentRepository.findById(id)
                .orElseThrow(TreatmentNotFoundException::new);
        return Optional.of(treatment);
    }

    @Override
    public Optional<Treatment> createTreatment(String name) {
        return Optional.of(treatmentRepository.save(new Treatment(name)));
    }

    @Override
    public Optional<Treatment> updateTreatment(Long id, String name, List<Long> salonTreatmentIds) {
        Treatment treatment = getTreatmentById(id).get();

        treatment.setName(name);
        treatment.setSalonTreatments(salonTreatmentRepository.findAllById(salonTreatmentIds));

        return Optional.of(treatmentRepository.save(treatment));
    }

    @Override
    public Optional<Treatment> deleteTreatmentById(Long id) {
        Treatment treatment = getTreatmentById(id).get();
        treatmentRepository.deleteById(id);
        return Optional.of(treatment);
    }
}
