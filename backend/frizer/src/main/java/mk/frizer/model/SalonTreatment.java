package mk.frizer.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class SalonTreatment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Salon salon;
    @ManyToOne
    private Treatment treatment;
    private Double price;

    public SalonTreatment(Salon salon, Treatment treatment, Double price) {
        this.salon = salon;
        this.treatment = treatment;
        this.price = price;
    }
}
