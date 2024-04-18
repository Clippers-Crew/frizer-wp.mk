package mk.frizer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class SalonService {
    @Id
    private Long id;
    @ManyToOne
    private Salon salon;
    @ManyToOne
    private Service service;
    private Double price;
}
