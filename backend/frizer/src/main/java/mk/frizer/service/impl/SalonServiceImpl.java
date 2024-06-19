package mk.frizer.service.impl;

import jakarta.transaction.Transactional;
import mk.frizer.model.*;
import mk.frizer.model.dto.SalonAddDTO;
import mk.frizer.model.dto.SalonUpdateDTO;
import mk.frizer.model.dto.TagAddDTO;
import mk.frizer.model.events.SalonCreatedEvent;
import mk.frizer.model.events.SalonUpdatedEvent;
import mk.frizer.model.exceptions.SalonNotFoundException;
import mk.frizer.model.exceptions.TagNotFoundException;
import mk.frizer.model.exceptions.UserNotFoundException;
import mk.frizer.repository.BusinessOwnerRepository;
import mk.frizer.repository.EmployeeRepository;
import mk.frizer.repository.SalonRepository;
import mk.frizer.repository.TagRepository;
import mk.frizer.service.SalonService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SalonServiceImpl implements SalonService {
    private final SalonRepository salonRepository;
    private final BusinessOwnerRepository businessOwnerRepository;
    private final EmployeeRepository employeeRepository;
    private final TagRepository tagRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private static final String UPLOAD_DIR = "src/main/resources/static/salons/";

    public SalonServiceImpl(SalonRepository salonRepository, BusinessOwnerRepository businessOwnerRepository, EmployeeRepository employeeRepository, TagRepository tagRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.salonRepository = salonRepository;
        this.businessOwnerRepository = businessOwnerRepository;
        this.employeeRepository = employeeRepository;
        this.tagRepository = tagRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public List<Salon> getSalons() {
        return salonRepository.findAll();
    }

    @Override
    public Optional<Salon> getSalonById(Long id) throws SalonNotFoundException{
        Salon salon = salonRepository.findById(id)
                .orElseThrow(SalonNotFoundException::new);
        return Optional.of(salon);
    }

    @Override
    @Transactional
    public Optional<Salon> createSalon(SalonAddDTO salonAddDTO) {
        BusinessOwner businessOwner = businessOwnerRepository.findById(salonAddDTO.getBusinessOwnerId())
                .orElseThrow(UserNotFoundException::new);

        Salon salon = new Salon(salonAddDTO.getName(), salonAddDTO.getDescription(), salonAddDTO.getLocation(),
                salonAddDTO.getPhoneNumber(), businessOwner);

        salonRepository.save(salon);

        applicationEventPublisher.publishEvent(new SalonCreatedEvent(salon));
        return Optional.of(salon);
    }

    @Override
    @Transactional
    public Optional<Salon> updateSalon(Long id, SalonUpdateDTO salonUpdateDTO) {
        Salon salon = getSalonById(id)
                .orElseThrow(SalonNotFoundException::new);

        salon.setName(salonUpdateDTO.getName());
        salon.setDescription(salonUpdateDTO.getDescription());
        salon.setLocation(salonUpdateDTO.getLocation());
        salon.setPhoneNumber(salonUpdateDTO.getPhoneNumber());
        salonRepository.save(salon);

        applicationEventPublisher.publishEvent(new SalonUpdatedEvent(salon));

        return Optional.of(salon);
    }

    @Override
    @Transactional
    public Optional<Salon> deleteSalonById(Long id) {
        Salon salon = getSalonById(id)
                .orElseThrow(SalonNotFoundException::new);
        salonRepository.deleteById(id);
        return Optional.of(salon);
    }

    @Override
    @Transactional
    public Optional<Salon> addTagToSalon(TagAddDTO tagAddDTO) {
        Salon salon = getSalonById(tagAddDTO.getSalonId()).get();
        Tag tag = tagRepository.findById(tagAddDTO.getTagId())
                .orElseThrow(TagNotFoundException::new);
        salon.getTags().add(tag);
        return Optional.of(salonRepository.save(salon));
    }

    @Override
    @Transactional
    public Optional<Salon> addTreatmentToSalon(Treatment treatment) {
        Salon salon = getSalonById(treatment.getSalon().getId()).get();
        salon.getSalonTreatments().add(treatment);
        salonRepository.save(salon);
        return Optional.of(salon);
    }

    @Override
    @Transactional
    public Optional<Salon> editTreatmentForSalon(Treatment treatment) {
        Salon salon = getSalonById(treatment.getSalon().getId()).get();

        salon.setSalonTreatments(salon.getSalonTreatments()
                .stream().map(item -> {
                    if(item.getId().equals(treatment.getId())) {
                        return treatment;
                    }
                   return item;
                }).collect(Collectors.toList()));
        salonRepository.save(salon);
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Salon> saveImage(Long id, MultipartFile image) throws IOException {
        String dirPath = String.format("%s/salon_%d", UPLOAD_DIR, id);

        File directory = new File(dirPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        Path filePath = Paths.get(dirPath, fileName);
        Files.write(filePath, image.getBytes());

        Optional<Salon> salon = getSalonById(id);
        if(salon.isPresent()){
            salon.get().getImagePaths().add(filePath.toString().replace("static/",""));
            salonRepository.save(salon.get());
            return salon;
        }
        return Optional.empty();
    }
}
