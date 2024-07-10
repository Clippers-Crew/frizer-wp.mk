package mk.frizer.utilities;

import mk.frizer.model.Appointment;
import mk.frizer.model.AppointmentTimeSlot;
import mk.frizer.repository.AppointmentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TimeSlotGenerator {
    private final AppointmentRepository appointmentRepository;
    private static final int SLOT_DURATION_MINUTES = 20;
    private static final LocalTime START_SHIFT_TIME = LocalTime.of(8, 0);
    private static final LocalTime END_SHIFT_TIME = LocalTime.of(20, 0);

    public TimeSlotGenerator(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }


    public static List<AppointmentTimeSlot> getAvailableSlots(LocalTime openTime, LocalTime closeTime, LocalDateTime date, List<Appointment> takenAppointments, Integer durationMultiplier) {
        List<AppointmentTimeSlot> allSlots = generateAllSlots(openTime, closeTime, date, durationMultiplier);
        List<AppointmentTimeSlot> availableSlots = allSlots.stream()
                .filter(slot -> isSlotAvailable(slot, takenAppointments))
                .collect(Collectors.toList());
        return availableSlots;
    }

    private static List<AppointmentTimeSlot> generateAllSlots(LocalTime openTime, LocalTime closeTime, LocalDateTime date, Integer durationMultiplier) {
        List<AppointmentTimeSlot> allSlots = new ArrayList<>();
        LocalDateTime currentSlot = date.withHour(openTime.getHour()).withMinute(openTime.getMinute());
        LocalDateTime now = LocalDateTime.now();
        LocalTime currentTime = now.toLocalTime();
        LocalDateTime endOfDay = date.withHour(closeTime.getHour()).withMinute(closeTime.getMinute());

        if (currentTime.isAfter(closeTime)) {
            currentSlot = currentSlot.plusDays(1);
        } else if(currentTime.isAfter(openTime) && currentTime.isBefore(closeTime)){
            currentSlot = now.plusHours(1).truncatedTo(ChronoUnit.HOURS);
        }

        while (currentSlot.plusMinutes((long) SLOT_DURATION_MINUTES * durationMultiplier).isBefore(endOfDay) ||
                currentSlot.plusMinutes((long) SLOT_DURATION_MINUTES * durationMultiplier).equals(endOfDay)) {
            LocalDateTime slotEnd = currentSlot.plusMinutes((long) SLOT_DURATION_MINUTES * durationMultiplier);
            allSlots.add(new AppointmentTimeSlot(currentSlot, slotEnd));
            currentSlot = currentSlot.plusMinutes(SLOT_DURATION_MINUTES);
        }

        return allSlots;
    }

    private static boolean isSlotAvailable(AppointmentTimeSlot slot, List<Appointment> takenAppointments) {
        for (Appointment taken : takenAppointments) {
            if(slot.getFrom().isBefore(taken.getDateTo()) && slot.getTo().isAfter(taken.getDateFrom()))
                return false;
        }
        return true;
    }


    public List<AppointmentTimeSlot> generateAvailableTimeSlots(Long salonId, Long employeeId, Integer durationMultiplier) {
        List<Appointment> reservedApointments = appointmentRepository.findAll()
                .stream().filter(a -> a.getSalon().getId().equals(salonId)
                        && a.getEmployee().getId().equals(employeeId)).toList();
        List<AppointmentTimeSlot> availableAppointmentTimeSlots = getAvailableSlots(START_SHIFT_TIME, END_SHIFT_TIME, LocalDateTime.now(), reservedApointments, durationMultiplier);
        for(int i=1; i< 10; i++){
            availableAppointmentTimeSlots.addAll(getAvailableSlots(START_SHIFT_TIME, END_SHIFT_TIME, LocalDateTime.of(LocalDate.now().plusDays(i), START_SHIFT_TIME), reservedApointments, durationMultiplier));
        }

        return availableAppointmentTimeSlots;
    }
}
