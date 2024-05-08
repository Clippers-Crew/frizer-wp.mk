package mk.frizer.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import mk.frizer.model.enums.Role;

@Data
public class BaseUserAddDTO {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
