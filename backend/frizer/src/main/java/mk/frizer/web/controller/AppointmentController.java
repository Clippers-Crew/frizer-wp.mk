package mk.frizer.web.controller;

import mk.frizer.model.*;
import mk.frizer.model.exceptions.EmployeeNotFoundException;
import mk.frizer.model.exceptions.SalonNotFoundException;
import mk.frizer.service.*;
import mk.frizer.utilities.TimeSlotGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {
    private final SalonService salonService;
    private final TagService tagService;
    private final ReviewService reviewService;
    private final EmployeeService employeeService;
    private final TimeSlotGenerator timeSlotGenerator;
    private final TreatmentService treatmentService;

    public AppointmentController(SalonService salonService, TagService tagService, ReviewService reviewService, EmployeeService employeeService, TimeSlotGenerator timeSlotGenerator, TreatmentService treatmentService) {
        this.salonService = salonService;
        this.tagService = tagService;
        this.reviewService = reviewService;
        this.employeeService = employeeService;
        this.timeSlotGenerator = timeSlotGenerator;
        this.treatmentService = treatmentService;
    }

    @GetMapping("/{id}")
    public String getHomePage(@PathVariable Long id, Model model) {

        Salon salon = salonService.getSalonById(id).orElseThrow(SalonNotFoundException::new);
        List<Employee> employees = employeeService.getEmployeesForSalon(id);
        List<Review> reviews = reviewService.getReviewsForEmployees(employees);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        ReviewStats salonStats = reviewService.getStatisticsForSalon(salon);
        Map<Long, ReviewStats> employeeMap = reviewService.getStatisticsForEmployee(employees);

        model.addAttribute("employeeMap", employeeMap);
        model.addAttribute("salon", salon);
        model.addAttribute("employees", employees);
        model.addAttribute("reviews", reviews);
        model.addAttribute("formatter",formatter);
        model.addAttribute("salonStats",salonStats);
        model.addAttribute("bodyContent", "employees");
        return "master-template";
    }

    @GetMapping("/reserve/{id}")
    public String getAvailableAppointments(Model model, @PathVariable Long id, @RequestParam Long salonId) {

        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusDays(10);
        List<Employee> employees = employeeService.getEmployeesForSalon(id);
        Salon salon = salonService.getSalonById(salonId).orElseThrow(SalonNotFoundException::new);
        List<Review> reviews = reviewService.getReviewsForEmployees(employees);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        ReviewStats salonStats = reviewService.getStatisticsForSalon(salon);
        Map<Long, ReviewStats> employeeMap = reviewService.getStatisticsForEmployee(employees);
        Employee employee = employeeService.getEmployeeById(id).orElseThrow(EmployeeNotFoundException::new);
        List<LocalDateTime> availableTimeSlots = timeSlotGenerator.generateAvailableTimeSlots(start, end);
        ReviewStats employeeStats = reviewService.getStatisticsForEmployee(employee);

        model.addAttribute("employeeMap", employeeMap);
        model.addAttribute("salon", salon);
        model.addAttribute("employees", employees);
        model.addAttribute("reviews", reviews);
        model.addAttribute("formatter",formatter);
        model.addAttribute("salonStats",salonStats);
        model.addAttribute("salon",salon);
        model.addAttribute("employeeStats",employeeStats);
        model.addAttribute("employee",employee);
        model.addAttribute("availableTimeSlots", availableTimeSlots);
        model.addAttribute("bodyContent", "appointments");
        return "master-template";
    }
}
