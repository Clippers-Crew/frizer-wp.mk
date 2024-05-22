package mk.frizer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.frizer.model.enums.Role;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(fetch = FetchType.EAGER)
    //@JoinColumn(name = "customer_id")
    private List<Appointment> appointmentsActive;
    @OneToMany(fetch = FetchType.EAGER)
    //@JoinColumn(name = "customer_id")
    private List<Appointment> appointmentsHistory;
    @OneToOne
    @JoinColumn(name = "base_user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private BaseUser baseUser;
    public Customer(BaseUser baseUser) {
        this.baseUser = baseUser;
        this.appointmentsActive = new ArrayList<>();
        this.appointmentsHistory = new ArrayList<>();
    }
}