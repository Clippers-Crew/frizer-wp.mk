package mk.frizer.service;

import mk.frizer.model.Salon;
import mk.frizer.model.dto.SalonAddDTO;
import mk.frizer.model.dto.SalonUpdateDTO;

import java.util.List;
import java.util.Optional;

public interface SalonService {
    List<Salon> getSalons();
    Optional<Salon> getSalonById(Long id);
    Optional<Salon> createSalon(SalonAddDTO salonAddDTO);
    Optional<Salon> updateSalon(Long id, SalonUpdateDTO salonUpdateDTO);
    Optional<Salon> deleteSalonById(Long id);
}
