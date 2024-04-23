package mk.frizer.service.impl;

import mk.frizer.model.*;
import mk.frizer.model.enums.Role;
import mk.frizer.model.exceptions.AppointmentNotFoundException;
import mk.frizer.model.exceptions.EmployeeNotFoundException;
import mk.frizer.model.exceptions.SalonNotFoundException;
import mk.frizer.model.exceptions.UserNotFoundException;
import mk.frizer.repository.*;
import mk.frizer.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final AppointmentRepository appointmentRepository;
    private final BaseUserRepository baseUserRepository;
    private final ReviewRepository reviewRepository;
    private final SalonRepository salonRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, AppointmentRepository appointmentRepository, BaseUserRepository baseUserRepository, ReviewRepository reviewRepository, SalonRepository salonRepository) {
        this.employeeRepository = employeeRepository;
        this.appointmentRepository = appointmentRepository;
        this.baseUserRepository = baseUserRepository;
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
    public Optional<Employee> createEmployee(Long baseUserId, Long salonId) {
        BaseUser baseUser = baseUserRepository.findById(baseUserId)
                .orElseThrow(UserNotFoundException::new);
        Salon salon = salonRepository.findById(salonId)
                .orElseThrow(SalonNotFoundException::new);

        Employee employee = new Employee(baseUser, salon);
        baseUser.setRole(Role.ROLE_EMPLOYEE);

        baseUserRepository.save(baseUser);
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
