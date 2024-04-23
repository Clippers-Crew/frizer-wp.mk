package mk.frizer.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.frizer.model.enums.Role;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class BusinessOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "owner")
    List<Salon> salonList;
    @OneToOne
    @JoinColumn(name = "base_user_id")
    private BaseUser baseUser;
    public BusinessOwner(BaseUser baseUser) {
        this.baseUser = baseUser;
        this.salonList = new ArrayList<>();

    }
    @Override
    public String toString() {
        return "BusinessOwner{" +
                "id=" + id +
                ", salonList= " + salonList +
                "}";
    }
}
