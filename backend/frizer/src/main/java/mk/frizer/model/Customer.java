package mk.frizer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends BaseUser{
    @Id
    private Long id;
    @OneToMany(mappedBy = "customer")
    List<Appointment> appointments;
}
