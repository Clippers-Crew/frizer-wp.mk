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
public class Employee {
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
    @OneToOne
    @JoinColumn(name = "base_user_id")
    private BaseUser baseUser;
    public Employee(BaseUser baseUser,Salon salon) {
        this.baseUser = baseUser;
        this.reviews = new ArrayList<>();
        this.appointmentsActive = new ArrayList<>();
        this.appointmentsHistory = new ArrayList<>();
        this.salon = salon;
    }


}
