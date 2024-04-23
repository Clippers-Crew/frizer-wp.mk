package mk.frizer.service;

import mk.frizer.model.Appointment;
import mk.frizer.model.BaseUser;
import mk.frizer.model.Customer;
import mk.frizer.model.enums.Role;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<Customer> getCustomers();
    Optional<Customer> getCustomerById(Long id);
    Optional<Customer> createCustomer(Long baseUserId);
    Optional<Customer> deleteCustomerById(Long id);
    //TODO event listen for creation of appointment
//    Optional<Customer> addAppointmentForCustomer(Long appointmentId);
}
