package mk.frizer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Salon {
    @Id
    private Long id;
    private String name;
    private String description;
    private String location;
    private String phoneNumber;
    // TODO Lista od sliki
    @OneToMany(mappedBy = "salon")
    List<Employee> employees;
    @OneToMany(mappedBy = "service")
    List<SalonService> salonServices;
    @ManyToOne
    private BusinessOwner owner;
}
