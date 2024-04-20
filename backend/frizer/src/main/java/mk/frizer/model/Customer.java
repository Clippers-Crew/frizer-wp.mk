package mk.frizer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.frizer.model.enums.Role;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Customer extends BaseUser{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    List<Appointment> appointments;

    public Customer(String email, String password, String firstName, String lastName, String phoneNumber, Role role) {
        super(email, password, firstName, lastName, phoneNumber, role);
        this.appointments = new ArrayList<>();
    }
}
