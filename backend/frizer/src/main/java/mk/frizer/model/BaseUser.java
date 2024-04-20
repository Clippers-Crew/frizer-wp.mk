package mk.frizer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.frizer.model.enums.Role;

@Data
@Entity
@NoArgsConstructor
public class BaseUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public BaseUser(String email, String password, String firstName, String lastName, String phoneNumber, Role role) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    @Override
    public String toString() {
        return "BaseUser{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", role=" + role +
                '}';
    }

}
