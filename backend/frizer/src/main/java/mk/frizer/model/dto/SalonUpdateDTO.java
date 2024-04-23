package mk.frizer.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SalonUpdateDTO {
    private String name;
    private String description;
    private String location;
    private String phoneNumber;
}
