package mk.frizer.service.impl;

import mk.frizer.model.Appointment;
import mk.frizer.model.BusinessOwner;
import mk.frizer.model.Customer;
import mk.frizer.model.Salon;
import mk.frizer.model.enums.Role;
import mk.frizer.model.exceptions.CustomerNotFoundException;
import mk.frizer.model.exceptions.UserNotFoundException;
import mk.frizer.repository.AppointmentRepository;
import mk.frizer.repository.BusinessOwnerRepository;
import mk.frizer.repository.CustomerRepository;
import mk.frizer.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final AppointmentRepository appointmentRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository, AppointmentRepository appointmentRepository) {
        this.customerRepository = customerRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(CustomerNotFoundException::new);
        return Optional.of(customer);
    }

    @Override
    public Optional<Customer> createCustomer(String email, String password, String firstName, String lastName, String phoneNumber, Role role) {
        Customer customer = new Customer(email, password, firstName, lastName, phoneNumber, role);
        return Optional.of(customerRepository.save(customer));
    }

    @Override
    public Optional<Customer> updateCustomer(Long id, String email, String password, String firstName, String lastName, String phoneNumber, Role role, List<Long> appointmentIds) {
        Customer customer = getCustomerById(id).get();

        customer.setEmail(email);
        customer.setPassword(password);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setPhoneNumber(phoneNumber);
        customer.setRole(role);
        customer.setAppointments(appointmentRepository.findAllById(appointmentIds));

        return Optional.of(customerRepository.save(customer));
    }

    @Override
    public Optional<Customer> deleteCustomerById(Long id) {
        //try catch?
        Customer customer = getCustomerById(id).get();
        customerRepository.deleteById(id);
        return Optional.of(customer);
    }
}
