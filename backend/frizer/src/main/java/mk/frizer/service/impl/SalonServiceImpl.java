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
import mk.frizer.utilities.DistanceCalculator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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
    private final DistanceCalculator distanceCalculator;
    private static final String UPLOAD_DIR = "src/main/resources/static/images/salons/";

    public SalonServiceImpl(SalonRepository salonRepository, BusinessOwnerRepository businessOwnerRepository, EmployeeRepository employeeRepository, TagRepository tagRepository, ApplicationEventPublisher applicationEventPublisher, DistanceCalculator distanceCalculator) {
        this.salonRepository = salonRepository;
        this.businessOwnerRepository = businessOwnerRepository;
        this.employeeRepository = employeeRepository;
        this.tagRepository = tagRepository;
        this.applicationEventPublisher = applicationEventPublisher;
        this.distanceCalculator = distanceCalculator;
    }

    @Override
    public List<Salon> getSalons() {
        return salonRepository.findAll();
    }

    @Override
    public Optional<Salon> getSalonById(Long id) throws SalonNotFoundException {
        Salon salon = salonRepository.findById(id).orElseThrow(SalonNotFoundException::new);
        return Optional.of(salon);
    }

    @Override
    @Transactional
    public Optional<Salon> createSalon(SalonAddDTO salonAddDTO) {
        BusinessOwner businessOwner = businessOwnerRepository.findById(salonAddDTO.getBusinessOwnerId()).orElseThrow(UserNotFoundException::new);

        Salon salon = new Salon(salonAddDTO.getName(), salonAddDTO.getDescription(), salonAddDTO.getLocation(), salonAddDTO.getPhoneNumber(), businessOwner,salonAddDTO.getRating(),salonAddDTO.getLatitude(),salonAddDTO.getLongitude());

        salonRepository.save(salon);

        applicationEventPublisher.publishEvent(new SalonCreatedEvent(salon));
        return Optional.of(salon);
    }

    @Override
    @Transactional
    public Optional<Salon> updateSalon(Long id, SalonUpdateDTO salonUpdateDTO) {
        Salon salon = getSalonById(id).orElseThrow(SalonNotFoundException::new);

        salon.setName(salonUpdateDTO.getName());
        salon.setDescription(salonUpdateDTO.getDescription());
        salon.setLocation(salonUpdateDTO.getLocation());
        salon.setPhoneNumber(salonUpdateDTO.getPhoneNumber());
        salon.setRating(salonUpdateDTO.getRating());
        salon.setLatitude(salonUpdateDTO.getLatitude());
        salon.setLongitude(salonUpdateDTO.getLongitude());
        salonRepository.save(salon);

        applicationEventPublisher.publishEvent(new SalonUpdatedEvent(salon));

        return Optional.of(salon);
    }

    @Override
    @Transactional
    public Optional<Salon> deleteSalonById(Long id) {
        Salon salon = getSalonById(id).orElseThrow(SalonNotFoundException::new);
        salonRepository.deleteById(id);
        return Optional.of(salon);
    }

    @Override
    @Transactional
    public Optional<Salon> addTagToSalon(TagAddDTO tagAddDTO) {
        Salon salon = getSalonById(tagAddDTO.getSalonId()).get();
        Tag tag = tagRepository.findById(tagAddDTO.getTagId()).orElseThrow(TagNotFoundException::new);
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

        salon.setSalonTreatments(salon.getSalonTreatments().stream().map(item -> {
            if (item.getId().equals(treatment.getId())) {
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
        if (salon.isPresent()) {
            String fullPath = filePath.toString().replace("templates/", "");
            String pathAfterStatic = fullPath.substring(fullPath.indexOf("static"));
            salon.get().getImagePaths().add(pathAfterStatic);
            salonRepository.save(salon.get());
            return salon;
        }
        return Optional.empty();
    }

    @Override
    public List<Salon> filterSalons(String name, String city, Float distance, Float rating, String userLocation){
//        List<Salon> salonByName = salonRepository.findAllByNameContaining(name);
        List<Salon> salonByName = salonRepository.findAll()
                .stream().filter(salon -> salon.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();
        List<Salon> salonsByRating = salonRepository.findAllByRatingGreaterThanEqual(rating);
        List<Salon> salonsByLocation;
        if (!city.equals("Цела Македонија")) {
            salonsByLocation = salonRepository.findAllByLocationContaining(city);
        } else {
            salonsByLocation = this.getSalons();
        }
        List<Salon> salonsByDistance = this
                .getSalons()
                .stream()
                .filter(salon -> distance >= distanceCalculator.getDistance(userLocation, salon.getLatitude(), salon.getLongitude()))
                .toList();

        List<Salon> interceptSalons = new ArrayList<>(salonByName);
        interceptSalons.retainAll(salonsByRating);
        interceptSalons.retainAll(salonsByLocation);
        interceptSalons.retainAll(salonsByDistance);
        return interceptSalons;
    }

    @Override
    public List<String> getSalonsAsString(List<Salon> salons) {
        return salons.stream()
                .map(SalonAdapter::convertToString)
                .collect(Collectors.toList());
    }

    @Override
    public String getSalonAsString(Salon salon) {
        return SalonAdapter.convertToString(salon);
    }

}

