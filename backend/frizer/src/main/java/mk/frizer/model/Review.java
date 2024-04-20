package mk.frizer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private BaseUser userFrom;
    @ManyToOne
    private BaseUser userTo;
    private Double rating;
    private String comment;

    public Review(BaseUser userFrom, BaseUser userTo, Double rating, String comment) {
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.rating = rating;
        this.comment = comment;
    }
}
