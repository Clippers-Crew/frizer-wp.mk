package mk.frizer.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    @ManyToOne
    private Treatment treatment;
    @ManyToOne
    private Salon salon;
    @ManyToOne
    private Employee employee;
    @ManyToOne
    private Customer customer;
    private boolean attended;

    public Appointment(LocalDateTime dateFrom, LocalDateTime dateTo, Treatment treatment, Salon salon, Employee employee, Customer customer) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.treatment = treatment;
        this.salon = salon;
        this.employee = employee;
        this.customer = customer;
        this.attended = true;
    }
}
