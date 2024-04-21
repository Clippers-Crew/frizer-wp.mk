package mk.frizer.service.impl;

import mk.frizer.model.*;
import mk.frizer.model.enums.Role;
import mk.frizer.model.exceptions.AppointmentNotFoundException;
import mk.frizer.model.exceptions.CustomerNotFoundException;
import mk.frizer.model.exceptions.EmployeeNotFoundException;
import mk.frizer.model.exceptions.SalonNotFoundException;
import mk.frizer.repository.*;
import mk.frizer.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final AppointmentRepository appointmentRepository;
    private final ReviewRepository reviewRepository;
    private final SalonRepository salonRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, AppointmentRepository appointmentRepository, ReviewRepository reviewRepository, SalonRepository salonRepository) {
        this.employeeRepository = employeeRepository;
        this.appointmentRepository = appointmentRepository;
        this.reviewRepository = reviewRepository;
        this.salonRepository = salonRepository;
    }

    @Override
    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(EmployeeNotFoundException::new);
        return Optional.of(employee);
    }

    @Override
    public Optional<Employee> createEmployee(String email, String password, String firstName, String lastName, String phoneNumber, Role role, Long salonId) {
        Salon salon = salonRepository.findById(salonId)
                .orElseThrow(SalonNotFoundException::new);
        Employee employee = new Employee(email, password, firstName, lastName, phoneNumber, role, salon);
        return Optional.of(employeeRepository.save(employee));
    }

    @Override
    public Optional<Employee> updateEmployee(Long id, String email, String password, String firstName, String lastName, String phoneNumber, Role role, Long salonId, List<Long> reviewIds, List<Long> appointmentHistoryIds, List<Long> appointmentActiveIds) {
        Employee employee = getEmployeeById(id).get();
        Salon salon = salonRepository.findById(salonId)
                .orElseThrow(SalonNotFoundException::new);

        employee.setEmail(email);
        employee.setPassword(password);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setPhoneNumber(phoneNumber);
        employee.setRole(role);
        employee.setSalon(salon);
        employee.setAppointmentsActive(appointmentRepository.findAllById(appointmentActiveIds));
        employee.setAppointmentsHistory(appointmentRepository.findAllById(appointmentHistoryIds));
        employee.setReviews(reviewRepository.findAllById(reviewIds));

        return Optional.of(employeeRepository.save(employee));
    }

    @Override
    public Optional<Employee> deleteEmployeeById(Long id) {
        //try catch?
        Employee customer = getEmployeeById(id).get();
        employeeRepository.deleteById(id);
        return Optional.of(customer);
    }

    @Override
    public Optional<Employee> addActiveAppointmentForEmployee(Long employeeId, Long appointmentId) {
        Employee employee = getEmployeeById(employeeId).get();
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(AppointmentNotFoundException::new);
        employee.getAppointmentsActive().add(appointment);
        return Optional.of(employeeRepository.save(employee));
    }

    @Override
    public Optional<Employee> addHistoryAppointmentForEmployee(Long employeeId, Long appointmentId) {
        Employee employee = getEmployeeById(employeeId).get();
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(AppointmentNotFoundException::new);
        employee.getAppointmentsHistory().add(appointment);
        return Optional.of(employeeRepository.save(employee));    }
}
