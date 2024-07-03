package mk.frizer.utilities;

import mk.frizer.model.Appointment;
import mk.frizer.repository.AppointmentRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Component
public class TimeSlotGenerator {
    private final AppointmentRepository appointmentRepository;

    public TimeSlotGenerator(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }


    public List<LocalDateTime> generateAvailableTimeSlots(LocalDateTime start, LocalDateTime end) {
        List<LocalDateTime> availableTimeSlots = new ArrayList<>();
        LocalDateTime current = start;
        List<Appointment> appointments = appointmentRepository.findAll();
        while (current.isBefore(end)) {
            boolean isAvailable = true;
            for (Appointment appointment : appointments) {
                if (!current.isBefore(appointment.getDateFrom()) && current.isBefore(appointment.getDateTo())) {
                    isAvailable = false;
                    break;
                }
            }
            if (isAvailable) {
                availableTimeSlots.add(current);
            }
            current = current.plusMinutes(20);
        }

        return availableTimeSlots;
    }
}
