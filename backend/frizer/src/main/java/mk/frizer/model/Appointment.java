package mk.frizer.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {
    @Id
    private Long id;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    @ManyToOne
    private Service service;
    @ManyToOne
    private Salon salon;
    @ManyToOne
    private Employee employee;
    @ManyToOne
    private Customer customer;
    private boolean attended = true;
}
