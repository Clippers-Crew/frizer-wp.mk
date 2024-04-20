package mk.frizer.repository;

import mk.frizer.model.BaseUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseUserRepository extends JpaRepository<BaseUser, Long> {
}
