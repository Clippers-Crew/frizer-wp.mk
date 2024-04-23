package mk.frizer.web;

import mk.frizer.model.BusinessOwner;
import mk.frizer.service.BusinessOwnerService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
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
}
