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
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    //TODO smeneto e CascadeType.ALL, ne e testirano
    private List<Appointment> appointmentsActive;
    //TODO smeneto e CascadeType.ALL, ne e testirano
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Appointment> appointmentsHistory;
    @ManyToOne
    private Salon salon;
    @OneToOne
    @JoinColumn(name = "base_user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private BaseUser baseUser;
    public Employee(BaseUser baseUser, Salon salon) {
        this.baseUser = baseUser;
        this.appointmentsActive = new ArrayList<>();
        this.appointmentsHistory = new ArrayList<>();
        this.salon = salon;
    }
}
