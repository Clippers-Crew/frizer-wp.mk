package mk.frizer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.frizer.model.enums.Role;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BaseUser {
    @Id
    private Long id;
    @Column(unique = true)
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Role role;
}
