package mk.frizer.service;

import mk.frizer.model.Salon;
import mk.frizer.model.Tag;
import mk.frizer.model.Treatment;
import mk.frizer.model.dto.SalonAddDTO;
import mk.frizer.model.dto.SalonUpdateDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public interface SalonService {
    List<Salon> getSalons();
    Optional<Salon> getSalonById(Long id);
    Optional<Salon> createSalon(SalonAddDTO salonAddDTO);
    Optional<Salon> updateSalon(Long id, SalonUpdateDTO salonUpdateDTO);
    Optional<Salon> deleteSalonById(Long id);
    Optional<Salon> addTagToSalon(Long salonId, Long tagId);
    Optional<Salon> addTreatmentToSalon(Treatment treatment);
    Optional<Salon> editTreatmentForSalon(Treatment treatment);
    Optional<Salon> saveImage(Long id, MultipartFile image) throws IOException;
}
