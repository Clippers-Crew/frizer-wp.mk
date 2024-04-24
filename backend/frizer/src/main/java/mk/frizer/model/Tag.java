package mk.frizer.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name;
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "tags")
    private List<Salon> salonsWithTag;
    public Tag(String name) {
        this.name = name;
        salonsWithTag = new ArrayList<>();
    }
}
