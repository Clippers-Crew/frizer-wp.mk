package mk.frizer.model.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AppointmentAddDTO {
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private Long treatmentId;
    private Long salonId;
    private Long employeeId;
    private Long customerId;
}
