package mk.frizer.repository;

import mk.frizer.model.City;
import mk.frizer.model.Salon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends JpaRepository<City, String> {
    Optional<City> findByName(String name);
    Optional<City> findByNameEqualsIgnoreCase(String name);
}
