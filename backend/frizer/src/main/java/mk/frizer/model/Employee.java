package mk.frizer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Employee extends BaseUser{
    @Id
    private Long id;
    @OneToMany
    List<Review> reviews;
    @OneToMany
    List<Appointment> appointmentsActive;
    @OneToMany
    List<Appointment> appointmentsHistory;
    @ManyToOne
    private Salon salon;
}
