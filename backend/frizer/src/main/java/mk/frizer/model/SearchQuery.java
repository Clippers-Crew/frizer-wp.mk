package mk.frizer.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class SearchQuery implements Serializable {
    private String name;
    private Float rating;
    private Float distance;
    private String city;
    private List<Salon> salons;

    @Override
    public String toString() {
        return "SearchQuery{" +
                "name='" + name + '\'' +
                ", rating=" + rating +
                ", distance=" + distance +
                ", city='" + city + '\'' +
                ", salons=" + salons +
                '}';
    }
}