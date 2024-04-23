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
    //    public Employee(String email, String password, String firstName, String lastName, String phoneNumber, Role role, Salon salon) {
    List<Employee> getEmployees();
    Optional<Employee> getEmployeeById(Long id);
    Optional<Employee> createEmployee(Long baseUserId, Long salonId);
    Optional<Employee> deleteEmployeeById(Long id);

    //Add review, add app to history, add app to active
    // TODO add review event handler da slusa za review kreiran
    Optional<Employee> addActiveAppointmentForEmployee(Long employeeId, Long appointmentId);
    Optional<Employee> addHistoryAppointmentForEmployee(Long employeeId, Long appointmentId);
}
