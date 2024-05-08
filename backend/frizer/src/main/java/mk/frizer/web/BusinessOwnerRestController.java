package mk.frizer.web;

import mk.frizer.model.BaseUser;
import mk.frizer.model.BusinessOwner;
import mk.frizer.model.Treatment;
import mk.frizer.model.dto.BaseUserAddDTO;
import mk.frizer.model.dto.BaseUserUpdateDTO;
import mk.frizer.model.exceptions.UserNotFoundException;
import mk.frizer.service.BusinessOwnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
//TODO users/owners or just /owners ??
@RequestMapping("/api/users/owners")
@CrossOrigin(origins = {"localhost:3000","localhost:3001"})
public class BusinessOwnerRestController {
    private final BusinessOwnerService businessOwnerService;

    public BusinessOwnerRestController(BusinessOwnerService businessOwnerService) {
        this.businessOwnerService = businessOwnerService;
    }

    @GetMapping()
    public List<BusinessOwner> getAllOwners() {
        return businessOwnerService.getBusinessOwners();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusinessOwner> getTreatment(@PathVariable Long id){
        return this.businessOwnerService.getBusinessOwnerById(id)
                .map(owner -> ResponseEntity.ok().body(owner))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add/{id}")
    public ResponseEntity<BusinessOwner> save(@PathVariable Long id) {
        return this.businessOwnerService.createBusinessOwner(id)
                .map(user -> ResponseEntity.ok().body(user))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BusinessOwner> deleteById(@PathVariable Long id) {
        Optional<BusinessOwner> user = this.businessOwnerService.deleteBusinessOwnerById(id);
        try{
            this.businessOwnerService.getBusinessOwnerById(id);
            return ResponseEntity.badRequest().build();
        }
        catch(UserNotFoundException exception){
            return ResponseEntity.ok().body(user.get());
        }
    }
}
