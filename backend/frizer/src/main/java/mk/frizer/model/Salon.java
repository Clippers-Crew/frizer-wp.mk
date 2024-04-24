package mk.frizer.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @OneToMany(mappedBy = "salon", fetch = FetchType.EAGER)
    private List<Employee> employees;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "salon")
    private List<Treatment> salonTreatments;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Tag> tags;
    @ManyToOne
    private BusinessOwner owner;

    public Salon(String name, String description, String location, String phoneNumber, BusinessOwner owner) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.employees = new ArrayList<>();
        this.salonTreatments = new ArrayList<>();
        this.tags = new ArrayList<>();
        this.owner = owner;
    }


}
