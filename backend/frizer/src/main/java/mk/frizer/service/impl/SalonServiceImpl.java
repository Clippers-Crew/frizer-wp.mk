package mk.frizer.service.impl;

import mk.frizer.model.*;
import mk.frizer.model.exceptions.SalonNotFoundException;
import mk.frizer.model.exceptions.UserNotFoundException;
import mk.frizer.repository.BusinessOwnerRepository;
import mk.frizer.repository.EmployeeRepository;
import mk.frizer.repository.SalonRepository;
import mk.frizer.repository.SalonTreatmentRepository;
import mk.frizer.service.SalonService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalonServiceImpl implements SalonService {
    private final SalonRepository salonRepository;
    private final BusinessOwnerRepository businessOwnerRepository;
    private final SalonTreatmentRepository salonTreatmentRepository;
    private final EmployeeRepository employeeRepository;

    public SalonServiceImpl(SalonRepository salonRepository, BusinessOwnerRepository businessOwnerRepository, SalonTreatmentRepository salonTreatmentRepository, EmployeeRepository employeeRepository) {
        this.salonRepository = salonRepository;
        this.businessOwnerRepository = businessOwnerRepository;
        this.salonTreatmentRepository = salonTreatmentRepository;
        this.employeeRepository = employeeRepository;
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
    public Optional<Salon> createSalon(String name, String description, String location, String phoneNumber, Long businessOwnerId) {
        //publish event
        BusinessOwner businessOwner = businessOwnerRepository.findById(businessOwnerId)
                .orElseThrow(UserNotFoundException::new);

        Salon salon = new Salon(name, description, location, phoneNumber, businessOwner);
        return Optional.of(salonRepository.save(salon));
    }

    @Override
    public Optional<Salon> updateSalon(Long id, String name, String description, String location, String phoneNumber, List<Long> employeeIds, List<Long> salonTreatmentIds, Long businessOwnerId) {
        Salon salon = getSalonById(id)
                .orElseThrow(SalonNotFoundException::new);
        BusinessOwner businessOwner = businessOwnerRepository.findById(businessOwnerId)
                .orElseThrow(UserNotFoundException::new);
        List<Employee> employees = employeeRepository.findAllById(employeeIds);
        List<SalonTreatment> salonTreatments = salonTreatmentRepository.findAllById(salonTreatmentIds);

        salon.setName(name);
        salon.setDescription(description);
        salon.setLocation(location);
        salon.setPhoneNumber(phoneNumber);
        salon.setEmployees(employees);
        salon.setSalonTreatments(salonTreatments);
        salon.setOwner(businessOwner);

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
