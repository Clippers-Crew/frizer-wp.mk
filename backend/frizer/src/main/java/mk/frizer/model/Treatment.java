package mk.frizer.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Treatment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany
    private List<SalonTreatment> salonTreatments;

    public Treatment(String name) {
        this.name = name;
        this.salonTreatments = new ArrayList<>();
    }
}
