package mk.frizer.web;

import mk.frizer.model.BusinessOwner;
import mk.frizer.model.Customer;
import mk.frizer.model.exceptions.CustomerNotFoundException;
import mk.frizer.model.exceptions.UserNotFoundException;
import mk.frizer.service.BusinessOwnerService;
import mk.frizer.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping({"/api/customers", "/api/customer"})
@CrossOrigin(origins = {"localhost:3000","localhost:3001"})
public class CustomerRestController {
    private final CustomerService customerService;

    public CustomerRestController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping()
    public List<Customer> getAllOwners() {
        return customerService.getCustomers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id){
        return this.customerService.getCustomerById(id)
                .map(owner -> ResponseEntity.ok().body(owner))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add/{id}")
    public ResponseEntity<Customer> createCustomer(@PathVariable Long id) {
        return this.customerService.createCustomer(id)
                .map(user -> ResponseEntity.ok().body(user))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Customer> deleteCustomerById(@PathVariable Long id) {
        Optional<Customer> user = this.customerService.deleteCustomerById(id);
        try{
            this.customerService.getCustomerById(id);
            return ResponseEntity.badRequest().build();
        }
        catch(CustomerNotFoundException exception){
            return ResponseEntity.ok().body(user.get());
        }
    }
}
