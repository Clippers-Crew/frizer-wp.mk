package mk.frizer.service.impl;

import mk.frizer.model.*;
import mk.frizer.model.exceptions.*;
import mk.frizer.repository.*;
import mk.frizer.service.AppointmentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final TreatmentRepository treatmentRepository;
    private final CustomerRepository customerRepository;
    private final SalonRepository salonRepository;
    private final EmployeeRepository employeeRepository;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, TreatmentRepository treatmentRepository, CustomerRepository customerRepository, SalonRepository salonRepository, EmployeeRepository employeeRepository) {
        this.appointmentRepository = appointmentRepository;
        this.treatmentRepository = treatmentRepository;
        this.customerRepository = customerRepository;
        this.salonRepository = salonRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Appointment> getAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public Optional<Appointment> getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(AppointmentNotFoundException::new);
        return Optional.of(appointment);
    }

    @Override
    public Optional<Appointment> createAppointment(LocalDateTime from, LocalDateTime to, Long treatmentId, Long salonId, Long employeeId, Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);
        Salon salon = salonRepository.findById(salonId)
                .orElseThrow(SalonNotFoundException::new);
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(EmployeeNotFoundException::new);
        Treatment treatment = treatmentRepository.findById(treatmentId)
                .orElseThrow(TreatmentNotFoundException::new);

        Appointment appointment = new Appointment(from, to, treatment, salon, employee, customer);
        return Optional.of(appointmentRepository.save(appointment));
    }

    @Override
    public Optional<Appointment> updateAppointment(Long id, LocalDateTime from, LocalDateTime to, Long treatmentId, Long salonId, Long employeeId, Long customerId) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(AppointmentNotFoundException::new);
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);
        Salon salon = salonRepository.findById(salonId)
                .orElseThrow(SalonNotFoundException::new);
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(EmployeeNotFoundException::new);
        Treatment treatment = treatmentRepository.findById(treatmentId)
                .orElseThrow(TreatmentNotFoundException::new);

        appointment.setDateFrom(from);
        appointment.setDateTo(to);
        appointment.setTreatment(treatment);
        appointment.setSalon(salon);
        appointment.setEmployee(employee);
        appointment.setCustomer(customer);

        return Optional.of(appointmentRepository.save(appointment));
    }

    @Override
    public Optional<Appointment> deleteAppointmentById(Long id) {
        Optional<Appointment> appointment = getAppointmentById(id);
        appointmentRepository.deleteById(id);
        return appointment;
    }

    @Override
    public Optional<Appointment> changeUserAttendanceAppointment(Long id) {
        Appointment appointment = getAppointmentById(id).get();
        appointment.setAttended(!appointment.isAttended());
        appointmentRepository.save(appointment);
        return Optional.of(appointment);
    }
}
