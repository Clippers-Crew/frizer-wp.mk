package mk.frizer.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import mk.frizer.model.*;
import mk.frizer.model.dto.AppointmentAddDTO;
import mk.frizer.model.exceptions.EmployeeNotFoundException;
import mk.frizer.model.exceptions.SalonNotFoundException;
import mk.frizer.model.exceptions.TreatmentNotFoundException;
import mk.frizer.service.*;
import mk.frizer.service.impl.AppointmentServiceImpl;
import mk.frizer.utilities.DateTimeRounding;
import mk.frizer.utilities.TimeSlotGenerator;
import org.apache.coyote.Request;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
    private final CustomerService customerService;

    public AppointmentController(SalonService salonService, TagService tagService, ReviewService reviewService, EmployeeService employeeService, TimeSlotGenerator timeSlotGenerator, TreatmentService treatmentService, AppointmentServiceImpl appointmentServiceImpl, AppointmentServiceImpl appointmentService, CustomerService customerService) {
        this.salonService = salonService;
        this.tagService = tagService;
        this.reviewService = reviewService;
        this.employeeService = employeeService;
        this.timeSlotGenerator = timeSlotGenerator;
        this.treatmentService = treatmentService;
        this.appointmentService = appointmentService;
        this.customerService = customerService;
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
        model.addAttribute("bodyContent", "appointment-employees");
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

        List<AppointmentTimeSlot> availableTimeSlots = timeSlotGenerator.generateAvailableTimeSlots(salon, employee, chosenTreatment.getDurationMultiplier());

        ReviewStats employeeStats = reviewService.getStatisticsForEmployee(chosenEmployee);

        model.addAttribute("salon", chosenSalon);
        model.addAttribute("employee", chosenEmployee);
        model.addAttribute("treatment", chosenTreatment);

        model.addAttribute("formatter", formatter);
        model.addAttribute("salonStats", salonStats);
        model.addAttribute("employeeStats", employeeStats);
        model.addAttribute("availableTimeSlots", availableTimeSlots);
        model.addAttribute("bodyContent", "appointment-choose-app");

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
        model.addAttribute("startAppointmentTime", time);
        model.addAttribute("endAppointmentTime", time.plusMinutes(20L * chosenTreatment.getDurationMultiplier()));

        model.addAttribute("formatter", formatter);
        model.addAttribute("salonStats", salonStats);
        model.addAttribute("employeeStats", employeeStats);

        model.addAttribute("bodyContent", "appointment-confirm");
        return "master-template";
    }


    @PostMapping("/create")
    public String createAppointment(@RequestParam Long salon,
                                    @RequestParam Long treatment,
                                    @RequestParam Long employee,
                                    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime time,
                                    RedirectAttributes redirectAttributes, HttpServletRequest request) {
        Treatment chosenTreatment = null;
        try {
            Optional<Treatment> optionalOfTreatment = treatmentService.getTreatmentById(treatment);
            chosenTreatment = optionalOfTreatment.get();
        } catch (TreatmentNotFoundException e) {
            return "redirect:/app-error?message=" + "Treatment not found";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            redirectAttributes.addFlashAttribute("message", "You need to be logged in to create an appointment.");
            return "redirect:/login";
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        Customer loggedInCustomer = customerService.getCustomerByEmail(email)
                .orElse(null);
        Long customerId = null;

        if (loggedInCustomer != null) {
            customerId = loggedInCustomer.getId();
        }

        LocalDateTime dateTo = time.plusMinutes(20L * chosenTreatment.getDurationMultiplier());
        AppointmentAddDTO appointmentAddDTO =
                new AppointmentAddDTO(time, dateTo, treatment, salon, employee, customerId);

        appointmentService.createAppointment(appointmentAddDTO);
        return "redirect:/home";
    }

}
