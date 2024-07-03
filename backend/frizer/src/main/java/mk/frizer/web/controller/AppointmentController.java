package mk.frizer.web.controller;

import mk.frizer.model.*;
import mk.frizer.model.dto.AppointmentAddDTO;
import mk.frizer.model.exceptions.EmployeeNotFoundException;
import mk.frizer.model.exceptions.SalonNotFoundException;
import mk.frizer.model.exceptions.TreatmentNotFoundException;
import mk.frizer.service.*;
import mk.frizer.service.impl.AppointmentServiceImpl;
import mk.frizer.utilities.DateTimeRounding;
import mk.frizer.utilities.TimeSlotGenerator;
import org.springframework.format.annotation.DateTimeFormat;
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
    private final AppointmentServiceImpl appointmentService;

    public AppointmentController(SalonService salonService, TagService tagService, ReviewService reviewService, EmployeeService employeeService, TimeSlotGenerator timeSlotGenerator, TreatmentService treatmentService, AppointmentServiceImpl appointmentServiceImpl, AppointmentServiceImpl appointmentService) {
        this.salonService = salonService;
        this.tagService = tagService;
        this.reviewService = reviewService;
        this.employeeService = employeeService;
        this.timeSlotGenerator = timeSlotGenerator;
        this.treatmentService = treatmentService;
        this.appointmentService = appointmentService;
    }

    @GetMapping("/{id}")
    public String getHomePage(@PathVariable Long id, Model model, @RequestParam Long treatmentId) {

        Salon salon = salonService.getSalonById(id).orElseThrow(SalonNotFoundException::new);
        List<Employee> employees = employeeService.getEmployeesForSalon(id);
        List<Review> reviews = reviewService.getReviewsForEmployees(employees);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        ReviewStats salonStats = reviewService.getStatisticsForSalon(salon);
        Map<Long, ReviewStats> employeeMap = reviewService.getStatisticsForEmployee(employees);
        Treatment treatment = treatmentService.getTreatmentById(treatmentId).orElseThrow(TreatmentNotFoundException::new);
        model.addAttribute("treatment", treatment);
        model.addAttribute("employeeMap", employeeMap);
        model.addAttribute("salon", salon);
        model.addAttribute("employees", employees);
        model.addAttribute("reviews", reviews);
        model.addAttribute("formatter", formatter);
        model.addAttribute("salonStats", salonStats);
        model.addAttribute("bodyContent", "employees");
        return "master-template";
    }

    @GetMapping("/reserve/{id}")
    public String getAvailableAppointments(Model model, @PathVariable Long id, @RequestParam Long salonId, @RequestParam Long treatmentId) {

        LocalDateTime start = DateTimeRounding.roundToNextHour(LocalDateTime.now());
        LocalDateTime end = start.plusDays(10);
        List<Employee> employees = employeeService.getEmployeesForSalon(salonId);
        Salon salon = salonService.getSalonById(salonId).orElseThrow(SalonNotFoundException::new);
        List<Review> reviews = reviewService.getReviewsForEmployees(employees);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        ReviewStats salonStats = reviewService.getStatisticsForSalon(salon);
        Map<Long, ReviewStats> employeeMap = reviewService.getStatisticsForEmployee(employees);
        Employee employee = employeeService.getEmployeeById(id).orElseThrow(EmployeeNotFoundException::new);
        List<LocalDateTime> availableTimeSlots = timeSlotGenerator.generateAvailableTimeSlots(start, end);
        ReviewStats employeeStats = reviewService.getStatisticsForEmployee(employee);
        Treatment treatment = treatmentService.getTreatmentById(treatmentId).orElseThrow(TreatmentNotFoundException::new);
        model.addAttribute("treatment", treatment);
        model.addAttribute("employeeMap", employeeMap);
        model.addAttribute("salon", salon);
        model.addAttribute("employees", employees);
        model.addAttribute("reviews", reviews);
        model.addAttribute("formatter", formatter);
        model.addAttribute("salonStats", salonStats);
        model.addAttribute("salon", salon);
        model.addAttribute("employeeStats", employeeStats);
        model.addAttribute("employee", employee);
        model.addAttribute("availableTimeSlots", availableTimeSlots);
        model.addAttribute("bodyContent", "appointments");
        return "master-template";
    }

    @GetMapping("/confirm/{id}")
    public String confirmAppointment(@PathVariable Long id, Model model, @RequestParam Long salonId,
                                     @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime timeSlot,
                                     @RequestParam Long treatmentId) {
        List<Employee> employees = employeeService.getEmployeesForSalon(salonId);
        Salon salon = salonService.getSalonById(salonId).orElseThrow(SalonNotFoundException::new);
        List<Review> reviews = reviewService.getReviewsForEmployees(employees);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        ReviewStats salonStats = reviewService.getStatisticsForSalon(salon);
        Employee employee = employeeService.getEmployeeById(id).orElseThrow(EmployeeNotFoundException::new);
        ReviewStats employeeStats = reviewService.getStatisticsForEmployee(employee);
        Treatment treatment = treatmentService.getTreatmentById(treatmentId).orElseThrow(TreatmentNotFoundException::new);
        List<Tag> tags = tagService.getTagsForSalon(salonId);
        model.addAttribute("tags", tags);
        model.addAttribute("treatment", treatment);
        model.addAttribute("timeSlot", timeSlot);
        model.addAttribute("salon", salon);
        model.addAttribute("employees", employees);
        model.addAttribute("reviews", reviews);
        model.addAttribute("formatter", formatter);
        model.addAttribute("salonStats", salonStats);
        model.addAttribute("salon", salon);
        model.addAttribute("employeeStats", employeeStats);
        model.addAttribute("employee", employee);
        model.addAttribute("bodyContent", "confirmation");
        return "master-template";
    }


    // TODO: add customerId when login is implemented
    @PostMapping("/add")
    String addAppointment(@RequestParam Long salonId, @RequestParam Long treatmentId, @RequestParam Long employeeId,
                          @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime dateFrom) {
        LocalDateTime dateTo = dateFrom.plusMinutes(20);
        AppointmentAddDTO appointmentAddDTO =
                new AppointmentAddDTO(dateFrom, dateTo, treatmentId, salonId, employeeId, 1L);

        appointmentService.createAppointment(appointmentAddDTO);
        return "redirect:/home";
    }
}
