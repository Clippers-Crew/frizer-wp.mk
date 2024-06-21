package mk.frizer.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SalonAddDTO {
    private String name;
    private String description;
    private String location;
    private String phoneNumber;
    private Long businessOwnerId;
    private Float rating;
    private Float latitude;
    private Float longitude;
}
