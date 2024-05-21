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
import java.util.Iterator;
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
        Iterator<Employee> employeeIterator = employeeRepository.findAll().iterator();
        while (employeeIterator.hasNext()) {
            Employee employee = employeeIterator.next();
            Iterator<Appointment> appointmentIterator = employee.getAppointmentsActive().iterator();
            while (appointmentIterator.hasNext()) {

                Appointment appointment = appointmentIterator.next();
                if (appointment.getDateTo().isBefore(LocalDateTime.now())) {
                    Customer customer = appointment.getCustomer();

                    //Remove appointment from employees active appointments
                    appointmentIterator.remove();
                    customer.getAppointmentsActive().remove(appointment);

                    customer.getAppointmentsHistory().add(appointment);
                    employee.getAppointmentsHistory().add(appointment);

                    customerRepository.save(customer);
                    employeeRepository.save(employee);
                }
            }
        }
    }
}
