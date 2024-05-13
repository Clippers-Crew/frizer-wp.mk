package mk.frizer.service.impl;

import jakarta.transaction.Transactional;
import mk.frizer.model.Appointment;
import mk.frizer.model.Customer;
import mk.frizer.model.Employee;
import mk.frizer.repository.CustomerRepository;
import mk.frizer.repository.EmployeeRepository;
import mk.frizer.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AppointmentHistoryService implements mk.frizer.service.AppointmentHistoryService {
    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;

    public AppointmentHistoryService(EmployeeRepository employeeRepository, CustomerRepository customerRepository) {
        this.employeeRepository = employeeRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public void addAppointmentsToHistory() {
        for (Employee employee : employeeRepository.findAll()) {
            for (Appointment appointment : employee.getAppointmentsActive()) {
                if(appointment.getDateTo().isBefore(LocalDateTime.now())){
                    Customer customer = appointment.getCustomer();

                    customer.getAppointmentsActive().remove(appointment);
                    employee.getAppointmentsActive().remove(appointment);

                    customer.getAppointmentsHistory().add(appointment);
                    employee.getAppointmentsHistory().add(appointment);

                    customerRepository.save(customer);
                    employeeRepository.save(employee);
                }
            }
        }
    }
}
