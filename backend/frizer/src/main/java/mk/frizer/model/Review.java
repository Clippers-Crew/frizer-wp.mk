package mk.frizer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    @Id
    private Long id;
    @ManyToOne
    private BaseUser userFrom;
    @ManyToOne
    private BaseUser userTo;
    private Double rating;
    private String comment;
}
