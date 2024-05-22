package mk.frizer.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TreatmentAddDTO {
    private String name;
    private Long salonId;
    private Double price;
}
