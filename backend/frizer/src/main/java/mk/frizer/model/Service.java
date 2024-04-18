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
public class Service {
    @Id
    private Long id;
    private String name;
    @OneToMany(mappedBy = "salon")
    private List<SalonService> serviceSalons;
}
