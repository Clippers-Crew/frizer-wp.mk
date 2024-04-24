package mk.frizer.web;

import mk.frizer.model.Salon;
import mk.frizer.model.Treatment;
import mk.frizer.model.dto.SalonAddDTO;
import mk.frizer.model.dto.SalonUpdateDTO;
import mk.frizer.model.dto.TreatmentUpdateDTO;
import mk.frizer.service.SalonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salons")
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
    public ResponseEntity<Salon> save(@RequestBody SalonAddDTO salonAddDTO) {
        System.out.println(salonAddDTO);
        return this.salonService.createSalon(salonAddDTO)
                .map(salon -> ResponseEntity.ok().body(salon))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<Salon> update(@PathVariable Long id, @RequestBody SalonUpdateDTO salonUpdateDTO) {
        return this.salonService.updateSalon(id, salonUpdateDTO)
                .map(salon -> ResponseEntity.ok().body(salon))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Salon> deleteById(@PathVariable Long id) {
        this.salonService.deleteSalonById(id);
        if (this.salonService.getSalonById(id).isEmpty()) return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }
}
