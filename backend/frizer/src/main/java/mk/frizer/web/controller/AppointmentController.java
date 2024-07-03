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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @GetMapping("")
    public String getAppointments(@RequestParam Long salon, Model model, @RequestParam Long treatment) {
        Salon chosenSalon = null;
        Treatment chosenTreatment = null;
        try {
            Optional<Salon> optionalOfSalon = salonService.getSalonById(salon);
            Optional<Treatment> optionalOfTreatment = treatmentService.getTreatmentById(treatment);
            chosenSalon = optionalOfSalon.get();
            chosenTreatment = optionalOfTreatment.get();
        } catch (SalonNotFoundException e) {
            return "redirect:/app-error?message=" + "Salon not found";
        } catch (TreatmentNotFoundException e) {
            return "redirect:/app-error?message=" + "Treatment not found";
        }
        List<Employee> employees = employeeService.getEmployeesForSalon(salon);
        List<Review> reviews = reviewService.getReviewsForEmployees(employees);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        ReviewStats salonStats = reviewService.getStatisticsForSalon(chosenSalon);
        Map<Long, ReviewStats> employeeMap = reviewService.getStatisticsForEmployee(employees);

        model.addAttribute("treatment", chosenTreatment);
        model.addAttribute("employeeMap", employeeMap);
        model.addAttribute("salon", chosenSalon);
        model.addAttribute("employees", employees);
        model.addAttribute("reviews", reviews);
        model.addAttribute("formatter", formatter);
        model.addAttribute("salonStats", salonStats);
        model.addAttribute("bodyContent", "employees");
        return "master-template";
    }

    @GetMapping("/reserve")
    public String getAvailableAppointments(@RequestParam Long salon,
                                           @RequestParam Long treatment,
                                           @RequestParam Long employee, Model model) {
        Salon chosenSalon = null;
        Treatment chosenTreatment = null;
        Employee chosenEmployee = null;
        try {
            Optional<Salon> optionalOfSalon = salonService.getSalonById(salon);
            Optional<Treatment> optionalOfTreatment = treatmentService.getTreatmentById(treatment);
            Optional<Employee> employeeOptional = employeeService.getEmployeeById(employee);
            chosenSalon = optionalOfSalon.get();
            chosenTreatment = optionalOfTreatment.get();
            chosenEmployee =  employeeOptional.get();
        } catch (SalonNotFoundException e) {
            return "redirect:/app-error?message=" + "Salon not found";
        } catch (TreatmentNotFoundException e) {
            return "redirect:/app-error?message=" + "Treatment not found";
        } catch (EmployeeNotFoundException e) {
            return "redirect:/app-error?message=" + "Employee not found";
        }
        List<Employee> employees = employeeService.getEmployeesForSalon(salon);
        List<Review> reviews = reviewService.getReviewsForEmployees(employees);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        ReviewStats salonStats = reviewService.getStatisticsForSalon(chosenSalon);
        Map<Long, ReviewStats> employeeMap = reviewService.getStatisticsForEmployee(employees);

        List<AppointmentTimeSlot> availableTimeSlots = timeSlotGenerator.generateAvailableTimeSlots(salon, employee, chosenTreatment.getDurationMultiplier());

        ReviewStats employeeStats = reviewService.getStatisticsForEmployee(chosenEmployee);

        model.addAttribute("salon", chosenSalon);
        model.addAttribute("employee", chosenEmployee);
        model.addAttribute("treatment", chosenTreatment);
        model.addAttribute("employeeMap", employeeMap);
        model.addAttribute("formatter", formatter);
        model.addAttribute("salonStats", salonStats);
        model.addAttribute("employeeStats", employeeStats);
        model.addAttribute("availableTimeSlots", availableTimeSlots);
        model.addAttribute("bodyContent", "appointments");

        return "master-template";
    }

    @GetMapping("/confirm")
    public String confirmAppointment(@RequestParam Long salon,
                                     @RequestParam Long treatment,
                                     @RequestParam Long employee,
                                     @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime time,
                                     Model model) {
        Salon chosenSalon = null;
        Treatment chosenTreatment = null;
        Employee chosenEmployee = null;
        try {
            Optional<Salon> optionalOfSalon = salonService.getSalonById(salon);
            Optional<Treatment> optionalOfTreatment = treatmentService.getTreatmentById(treatment);
            Optional<Employee> employeeOptional = employeeService.getEmployeeById(employee);
            chosenSalon = optionalOfSalon.get();
            chosenTreatment = optionalOfTreatment.get();
            chosenEmployee =  employeeOptional.get();
        } catch (SalonNotFoundException e) {
            return "redirect:/app-error?message=" + "Salon not found";
        } catch (TreatmentNotFoundException e) {
            return "redirect:/app-error?message=" + "Treatment not found";
        } catch (EmployeeNotFoundException e) {
            return "redirect:/app-error?message=" + "Employee not found";
        }

        List<Employee> employees = employeeService.getEmployeesForSalon(salon);
        List<Review> reviews = reviewService.getReviewsForEmployees(employees);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        ReviewStats salonStats = reviewService.getStatisticsForSalon(chosenSalon);
        ReviewStats employeeStats = reviewService.getStatisticsForEmployee(chosenEmployee);

        model.addAttribute("salon", chosenSalon);
        model.addAttribute("treatment", chosenTreatment);
        model.addAttribute("employee", chosenEmployee);
        model.addAttribute("timeSlot", time);

        model.addAttribute("formatter", formatter);
        model.addAttribute("salonStats", salonStats);
        model.addAttribute("employeeStats", employeeStats);

        model.addAttribute("bodyContent", "confirmation");
        return "master-template";
    }


    // TODO: add customerId when login is implemented
    @PostMapping("/create")
    public String createAppointment(@RequestParam Long salon,
                                    @RequestParam Long treatment,
                                    @RequestParam Long employee,
                                    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime time) {
        Treatment chosenTreatment = null;
        try {
            Optional<Treatment> optionalOfTreatment = treatmentService.getTreatmentById(treatment);
            chosenTreatment = optionalOfTreatment.get();
        } catch (TreatmentNotFoundException e) {
            return "redirect:/app-error?message=" + "Treatment not found";
        }
        LocalDateTime dateTo = time.plusMinutes(20L * chosenTreatment.getDurationMultiplier());
        AppointmentAddDTO appointmentAddDTO =
                new AppointmentAddDTO(time, dateTo, treatment, salon, employee, 1L);

        appointmentService.createAppointment(appointmentAddDTO);
        return "redirect:/home";
    }

}
