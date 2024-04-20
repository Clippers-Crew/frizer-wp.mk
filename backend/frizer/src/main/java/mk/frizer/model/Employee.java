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
public class Employee extends BaseUser{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(fetch = FetchType.EAGER)
    List<Review> reviews;
    @OneToMany(fetch = FetchType.EAGER)
    List<Appointment> appointmentsActive;
    @OneToMany(fetch = FetchType.EAGER)
    List<Appointment> appointmentsHistory;
    @ManyToOne
    private Salon salon;

    public Employee(String email, String password, String firstName, String lastName, String phoneNumber, Role role, Salon salon) {
        super(email, password, firstName, lastName, phoneNumber, role);
        this.reviews = new ArrayList<>();
        this.appointmentsActive = new ArrayList<>();
        this.appointmentsHistory = new ArrayList<>();
        this.salon = salon;
    }
}
