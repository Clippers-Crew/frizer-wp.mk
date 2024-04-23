package mk.frizer.service.impl;

import mk.frizer.model.*;
import mk.frizer.model.dto.SalonAddDTO;
import mk.frizer.model.dto.SalonUpdateDTO;
import mk.frizer.model.events.SalonCreatedEvent;
import mk.frizer.model.exceptions.SalonNotFoundException;
import mk.frizer.model.exceptions.UserNotFoundException;
import mk.frizer.repository.BusinessOwnerRepository;
import mk.frizer.repository.EmployeeRepository;
import mk.frizer.repository.SalonRepository;
import mk.frizer.repository.SalonTreatmentRepository;
import mk.frizer.service.SalonService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalonServiceImpl implements SalonService {
    private final SalonRepository salonRepository;
    private final BusinessOwnerRepository businessOwnerRepository;
    private final SalonTreatmentRepository salonTreatmentRepository;
    private final EmployeeRepository employeeRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public SalonServiceImpl(SalonRepository salonRepository, BusinessOwnerRepository businessOwnerRepository, SalonTreatmentRepository salonTreatmentRepository, EmployeeRepository employeeRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.salonRepository = salonRepository;
        this.businessOwnerRepository = businessOwnerRepository;
        this.salonTreatmentRepository = salonTreatmentRepository;
        this.employeeRepository = employeeRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public List<Salon> getSalons() {
        return salonRepository.findAll();
    }

    @Override
    public Optional<Salon> getSalonById(Long id) {
        Salon salon = salonRepository.findById(id)
                .orElseThrow(SalonNotFoundException::new);
        return Optional.of(salon);
    }

    @Override
    public Optional<Salon> createSalon(SalonAddDTO salonAddDTO) {
        //publish event
        BusinessOwner businessOwner = businessOwnerRepository.findById(salonAddDTO.getBusinessOwnerId())
                .orElseThrow(UserNotFoundException::new);

        Salon salon = new Salon(salonAddDTO.getName(), salonAddDTO.getDescription(), salonAddDTO.getLocation(),
                salonAddDTO.getPhoneNumber(), businessOwner);

        salonRepository.save(salon);

        applicationEventPublisher.publishEvent(new SalonCreatedEvent(salon));
        return Optional.of(salon);
    }

    @Override
    public Optional<Salon> updateSalon(Long id, SalonUpdateDTO salonUpdateDTO) {
        Salon salon = getSalonById(id)
                .orElseThrow(SalonNotFoundException::new);

        salon.setName(salonUpdateDTO.getName());
        salon.setDescription(salonUpdateDTO.getDescription());
        salon.setLocation(salonUpdateDTO.getLocation());
        salon.setPhoneNumber(salonUpdateDTO.getPhoneNumber());

        return Optional.of(salonRepository.save(salon));
    }

    @Override
    public Optional<Salon> deleteSalonById(Long id) {
        Salon salon = getSalonById(id)
                .orElseThrow(SalonNotFoundException::new);
        salonRepository.deleteById(id);
        return Optional.of(salon);
    }
}
