package mk.frizer.service.impl;

import mk.frizer.model.Salon;
import mk.frizer.model.Treatment;
import mk.frizer.model.dto.TreatmentAddDTO;
import mk.frizer.model.dto.TreatmentUpdateDTO;
import mk.frizer.model.events.SalonCreatedEvent;
import mk.frizer.model.events.TreatmentCreatedEvent;
import mk.frizer.model.events.TreatmentUpdatedEvent;
import mk.frizer.model.exceptions.SalonNotFoundException;
import mk.frizer.model.exceptions.TreatmentNotFoundException;
import mk.frizer.repository.SalonRepository;
import mk.frizer.repository.TreatmentRepository;
import mk.frizer.service.TreatmentService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TreatmentServiceImpl implements TreatmentService {
    private final TreatmentRepository treatmentRepository;
    private final SalonRepository salonRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public TreatmentServiceImpl(TreatmentRepository treatmentRepository, SalonRepository salonRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.treatmentRepository = treatmentRepository;
        this.salonRepository = salonRepository;
        this.applicationEventPublisher = applicationEventPublisher;
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
    public Optional<Treatment> createTreatment(TreatmentAddDTO treatmentAddDTO) {
        Salon salon = salonRepository.findById(treatmentAddDTO.getSalonId())
                .orElseThrow(SalonNotFoundException::new);

        Treatment treatment = new Treatment(treatmentAddDTO.getName(), salon, treatmentAddDTO.getPrice());
        treatmentRepository.save(treatment);

        applicationEventPublisher.publishEvent(new TreatmentCreatedEvent(treatment));
        return Optional.of(treatment);
    }

    @Override
    public Optional<Treatment> updateTreatment(Long id, TreatmentUpdateDTO treatmentUpdateDTO) {
        Treatment treatment = getTreatmentById(id).get();
        treatment.setName(treatmentUpdateDTO.getName());
        treatment.setPrice(treatmentUpdateDTO.getPrice());
        treatmentRepository.save(treatment);

        applicationEventPublisher.publishEvent(new TreatmentUpdatedEvent(treatment));
        return Optional.of(treatment);
    }

    @Override
    public Optional<Treatment> deleteTreatmentById(Long id) {
        Treatment treatment = getTreatmentById(id).get();
        treatmentRepository.deleteById(id);
        return Optional.of(treatment);
    }
}
