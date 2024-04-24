package mk.frizer.service;

import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import mk.frizer.model.Appointment;
import mk.frizer.model.Customer;
import mk.frizer.model.Employee;
import mk.frizer.model.Review;
import mk.frizer.model.enums.Role;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<Employee> getEmployees();
    Optional<Employee> getEmployeeById(Long id);
    Optional<Employee> createEmployee(Long baseUserId, Long salonId);
    Optional<Employee> deleteEmployeeById(Long id);
    //add app to history, add app to active
    Optional<Employee> addActiveAppointmentForEmployee(Long employeeId, Long appointmentId);
    Optional<Employee> addHistoryAppointmentForEmployee(Long employeeId, Long appointmentId);
}
