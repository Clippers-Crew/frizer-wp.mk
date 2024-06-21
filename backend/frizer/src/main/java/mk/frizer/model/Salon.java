package mk.frizer.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Salon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String location;
    private String phoneNumber;

    //cascade = Cascade.ALL
    @OneToMany(mappedBy = "salon", fetch = FetchType.EAGER, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Employee> employees;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "salon")
    private List<Treatment> salonTreatments;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Tag> tags;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @Nullable
    private BusinessOwner owner;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> imagePaths;

    private Float rating;

    private Float latitude;

    private Float longitude;
    public Salon(String name, String description, String location, String phoneNumber,
                 BusinessOwner owner,Float rating,Float latitude, Float longitude) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.employees = new ArrayList<>();
        this.salonTreatments = new ArrayList<>();
        this.tags = new ArrayList<>();
        this.owner = owner;
        this.imagePaths = new ArrayList<>();
        this.rating = rating;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
