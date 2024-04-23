package mk.frizer.service.impl;

import mk.frizer.model.*;
import mk.frizer.model.exceptions.CustomerNotFoundException;
import mk.frizer.model.exceptions.UserNotFoundException;
import mk.frizer.repository.AppointmentRepository;
import mk.frizer.repository.BaseUserRepository;
import mk.frizer.repository.CustomerRepository;
import mk.frizer.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final AppointmentRepository appointmentRepository;
    private final CustomerRepository customerRepository;
    private final BaseUserRepository baseUserRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository, AppointmentRepository appointmentRepository, BaseUserRepository baseUserRepository) {
        this.customerRepository = customerRepository;
        this.appointmentRepository = appointmentRepository;
        this.baseUserRepository = baseUserRepository;
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
    public Optional<Customer> createCustomer(Long baseUserId) {
        BaseUser baseUser = baseUserRepository.findById(baseUserId)
                .orElseThrow(UserNotFoundException::new);

        Customer customer = new Customer(baseUser);
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
