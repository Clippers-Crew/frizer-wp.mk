package mk.frizer.model.dto;

import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import mk.frizer.model.Salon;

@Data
public class TreatmentAddDTO {
    private String name;
    private Long salonId;
    private Double price;
}
