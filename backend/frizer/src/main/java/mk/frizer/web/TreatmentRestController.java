package mk.frizer.web;

import mk.frizer.model.Salon;
import mk.frizer.model.Treatment;
import mk.frizer.model.dto.SalonAddDTO;
import mk.frizer.model.dto.SalonUpdateDTO;
import mk.frizer.model.dto.TreatmentAddDTO;
import mk.frizer.model.dto.TreatmentUpdateDTO;
import mk.frizer.service.TreatmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/treatments")
@CrossOrigin(origins = {"localhost:3000","localhost:3001"})
public class TreatmentRestController {
    private final TreatmentService treatmentService;

    public TreatmentRestController(TreatmentService treatmentService) {
        this.treatmentService = treatmentService;
    }

    @GetMapping()
    public List<Treatment> getTreatments(){
        return treatmentService.getTreatments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Treatment> getTreatment(@PathVariable Long id){
        return this.treatmentService.getTreatmentById(id)
                .map(treatment -> ResponseEntity.ok().body(treatment))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Treatment> save(@RequestBody TreatmentAddDTO treatmentAddDTO) {
        System.out.println(treatmentAddDTO);
        return this.treatmentService.createTreatment(treatmentAddDTO)
                .map(treatment -> ResponseEntity.ok().body(treatment))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<Treatment> update(@PathVariable Long id, @RequestBody TreatmentUpdateDTO treatmentUpdateDTO) {
        return this.treatmentService.updateTreatment(id, treatmentUpdateDTO)
                .map(treatment -> ResponseEntity.ok().body(treatment))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Treatment> deleteById(@PathVariable Long id) {
        this.treatmentService.deleteTreatmentById(id);
        if (this.treatmentService.getTreatmentById(id).isEmpty()) return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }
}
