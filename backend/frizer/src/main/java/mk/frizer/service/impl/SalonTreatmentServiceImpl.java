package mk.frizer.service.impl;

import mk.frizer.model.Salon;
import mk.frizer.model.SalonTreatment;
import mk.frizer.model.Treatment;
import mk.frizer.model.exceptions.SalonNotFoundException;
import mk.frizer.model.exceptions.SalonTreatmentNotFoundException;
import mk.frizer.model.exceptions.TreatmentNotFoundException;
import mk.frizer.repository.SalonRepository;
import mk.frizer.repository.SalonTreatmentRepository;
import mk.frizer.repository.TreatmentRepository;
import mk.frizer.service.SalonTreatmentService;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.util.List;
import java.util.Optional;

@Service
public class SalonTreatmentServiceImpl implements SalonTreatmentService {
    private final SalonTreatmentRepository salonTreatmentRepository;
    private final SalonRepository salonRepository;

    private final TreatmentRepository treatmentRepository;

    public SalonTreatmentServiceImpl(SalonTreatmentRepository salonTreatmentRepository, SalonRepository salonRepository, TreatmentRepository treatmentRepository) {
        this.salonTreatmentRepository = salonTreatmentRepository;
        this.salonRepository = salonRepository;
        this.treatmentRepository = treatmentRepository;
    }

    @Override
    public List<SalonTreatment> getSalonTreatments() {
        return salonTreatmentRepository.findAll();
    }

    @Override
    public Optional<SalonTreatment> getSalonTreatmentById(Long id) {
        return Optional.of(salonTreatmentRepository.findById(id)
                .orElseThrow(SalonTreatmentNotFoundException::new));
    }

    @Override
    public Optional<SalonTreatment> createSalonTreatment(Long salonId, Long treatmentId, Double price) {
        Salon salon = salonRepository.findById(salonId)
                .orElseThrow(SalonNotFoundException::new);
        Treatment treatment = treatmentRepository.findById(treatmentId)
                .orElseThrow(TreatmentNotFoundException::new);

        SalonTreatment salonTreatment = new SalonTreatment(salon,treatment,price);

        return Optional.of(salonTreatmentRepository.save(salonTreatment));
    }

    @Override
    public Optional<SalonTreatment> updateSalonTreatment(Long id, Long salonId, Long treatmentId, Double price) {
        Salon salon = salonRepository.findById(salonId)
                .orElseThrow(SalonNotFoundException::new);
        Treatment treatment = treatmentRepository.findById(treatmentId)
                .orElseThrow(TreatmentNotFoundException::new);

        SalonTreatment salonTreatment = getSalonTreatmentById(id).get();
        salonTreatment.setTreatment(treatment);
        salonTreatment.setSalon(salon);
        salonTreatment.setPrice(price);

        return Optional.of(salonTreatmentRepository.save(salonTreatment));
    }

    @Override
    public Optional<SalonTreatment> deleteSalonTreatmentById(Long id) {
        SalonTreatment salonTreatment = getSalonTreatmentById(id).get();
        salonTreatmentRepository.deleteById(id);
        return Optional.of(salonTreatment);
    }
}
