package mk.frizer.web;

import mk.frizer.model.Employee;
import mk.frizer.model.Salon;
import mk.frizer.model.Treatment;
import mk.frizer.model.dto.SalonAddDTO;
import mk.frizer.model.dto.SalonUpdateDTO;
import mk.frizer.model.dto.TreatmentUpdateDTO;
import mk.frizer.model.exceptions.SalonNotFoundException;
import mk.frizer.model.exceptions.UserNotFoundException;
import mk.frizer.service.SalonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping({"/api/salons", "/api/salon"})
@CrossOrigin(origins = {"localhost:3000","localhost:3001"})
public class SalonRestController {
    private final SalonService salonService;

    public SalonRestController(SalonService salonService) {
        this.salonService = salonService;
    }

    @GetMapping()
    public List<Salon> getAllSalons(){
        return salonService.getSalons();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Salon> getSalon(@PathVariable Long id){
        return this.salonService.getSalonById(id)
                .map(salon -> ResponseEntity.ok().body(salon))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Salon> createSalon(@RequestBody SalonAddDTO salonAddDTO) {
        return this.salonService.createSalon(salonAddDTO)
                .map(salon -> ResponseEntity.ok().body(salon))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<Salon> updateSalon(@PathVariable Long id, @RequestBody SalonUpdateDTO salonUpdateDTO) {
        return this.salonService.updateSalon(id, salonUpdateDTO)
                .map(salon -> ResponseEntity.ok().body(salon))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Salon> deleteSalonById(@PathVariable Long id) {
        Optional<Salon> salon = this.salonService.deleteSalonById(id);
        try{
            this.salonService.getSalonById(id);
            return ResponseEntity.badRequest().build();
        }
        catch(SalonNotFoundException exception){
            return ResponseEntity.ok().body(salon.get());
        }
    }
}
