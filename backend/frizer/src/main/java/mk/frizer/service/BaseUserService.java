package mk.frizer.service;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import mk.frizer.model.Appointment;
import mk.frizer.model.BaseUser;
import mk.frizer.model.enums.Role;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BaseUserService {
    List<BaseUser> getBaseUsers();

    Optional<BaseUser> getBaseUserById(Long id);

    Optional<BaseUser> createBaseUser(String email, String password, String firstName, String lastName, String phoneNumber, Role role);

    Optional<BaseUser> updateBaseUser(Long id, String email, String password, String firstName, String lastName, String phoneNumber, Role role);

    Optional<BaseUser> deleteBaseUserById(Long id);
}