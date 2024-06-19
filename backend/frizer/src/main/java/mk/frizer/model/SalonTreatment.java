package mk.frizer.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@NoArgsConstructor
public class SalonTreatment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Salon salon;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Treatment treatment;
    private Double price;

    public SalonTreatment(Salon salon, Treatment treatment, Double price) {
        this.salon = salon;
        this.treatment = treatment;
        this.price = price;
    }
}
