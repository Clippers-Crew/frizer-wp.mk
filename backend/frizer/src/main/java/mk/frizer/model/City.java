package mk.frizer.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class City {
    private String mkTitle;
    private String enTitle;
    private Float latitude;
    private Float longitude;

    public boolean checkCityIs(String city) {
        return mkTitle.toLowerCase().contains(city.toLowerCase())
                || enTitle.toLowerCase().contains(city.toLowerCase());
    }
}

