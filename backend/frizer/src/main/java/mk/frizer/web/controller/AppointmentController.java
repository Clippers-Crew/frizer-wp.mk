package mk.frizer.web.controller;

import mk.frizer.model.*;
import mk.frizer.model.exceptions.SalonNotFoundException;
import mk.frizer.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/appointments-salon")
public class AppointmentController {
    private final SalonService salonService;
    private final TagService tagService;
    private final ReviewService reviewService;
    private final EmployeeService employeeService;

    private final TreatmentService treatmentService;

    public AppointmentController(SalonService salonService, TagService tagService, ReviewService reviewService, EmployeeService employeeService, TreatmentService treatmentService) {
        this.salonService = salonService;
        this.tagService = tagService;
        this.reviewService = reviewService;
        this.employeeService = employeeService;
        this.treatmentService = treatmentService;
    }

    @GetMapping("/{id}")
    public String getHomePage(@PathVariable Long id, Model model) {

        Salon salon = salonService.getSalonById(id).orElseThrow(SalonNotFoundException::new);
        List<Employee> employees = employeeService.getEmployeesForSalon(id);
        List<Review> reviews = reviewService.getReviewsForEmployees(employees);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        Map<Long, ReviewStats> employeeMap = reviewService.getStatisticsForEmployee(employees);
        model.addAttribute("employeeMap", employeeMap);
        model.addAttribute("salon", salon);
        model.addAttribute("employees", employees);
        model.addAttribute("reviews", reviews);
        model.addAttribute("bodyContent", "employees");
        return "master-template";
    }
}
