package mk.frizer.web;

import mk.frizer.model.Employee;
import mk.frizer.model.dto.EmployeeAddDTO;
import mk.frizer.model.exceptions.UserNotFoundException;
import mk.frizer.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping({ "/api/employees", "/api/employee" })
@CrossOrigin(origins = { "localhost:3000", "localhost:3001" })
public class EmployeeRestController {
    private final EmployeeService employeeService;

    public EmployeeRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping()
    public List<Employee> getAllEmployees() {
        return employeeService.getEmployees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return this.employeeService.getEmployeeById(id)
                .map(owner -> ResponseEntity.ok().body(owner))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Employee> createEmployee(@RequestBody EmployeeAddDTO employeeAddDTO) {
        return this.employeeService.createEmployee(employeeAddDTO)
                .map(user -> ResponseEntity.ok().body(user))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Employee> deleteEmployeeById(@PathVariable Long id) {
        Optional<Employee> user = this.employeeService.deleteEmployeeById(id);
        try {
            this.employeeService.getEmployeeById(id);
            return ResponseEntity.badRequest().build();
        } catch (UserNotFoundException exception) {
            return ResponseEntity.ok().body(user.get());
        }
    }
}
