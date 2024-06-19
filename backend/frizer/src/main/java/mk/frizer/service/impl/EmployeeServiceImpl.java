package mk.frizer.service.impl;

import jakarta.transaction.Transactional;
import mk.frizer.model.*;
import mk.frizer.model.dto.EmployeeAddDTO;
import mk.frizer.model.enums.Role;
import mk.frizer.model.exceptions.*;
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
    @Transactional
    public Optional<Employee> createEmployee(EmployeeAddDTO employeeAddDTO) {
        Optional<Employee> employee = employeeRepository.findById(employeeAddDTO.getUserId());
        Salon salon = salonRepository.findById(employeeAddDTO.getSalonId())
                .orElseThrow(SalonNotFoundException::new);
        if(employee.isEmpty()){
            BaseUser baseUser = baseUserRepository.findById(employeeAddDTO.getUserId())
                    .orElseThrow(UserNotFoundException::new);

            employee = Optional.of(new Employee(baseUser, salon));
            baseUser.setRole(Role.ROLE_EMPLOYEE);
            baseUserRepository.save(baseUser);
        }
        employee.get().setSalon(salon);
        return Optional.of(employeeRepository.save(employee.get()));
    }

    @Override
    @Transactional
    public Optional<Employee> deleteEmployeeById(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isEmpty())
            throw new EmployeeNotFoundException();
//        appointmentRepository.deleteAll(employee.get().getAppointmentsActive());
        employeeRepository.deleteById(id);
        return employee;
    }

    @Override
    @Transactional
    public Optional<Employee> addActiveAppointmentForEmployee(Appointment appointment) {
        Employee employee = appointment.getEmployee();
        employee.getAppointmentsActive().add(appointment);
        return Optional.of(employeeRepository.save(employee));
    }

    @Override
    @Transactional
    public Optional<Employee> addHistoryAppointmentForEmployee(Appointment appointment) {
        Employee employee = appointment.getEmployee();
        employee.getAppointmentsActive().remove(appointment);
        employee.getAppointmentsHistory().add(appointment);
        return Optional.of(employeeRepository.save(employee));
    }
}
