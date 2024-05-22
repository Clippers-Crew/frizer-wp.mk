package mk.frizer.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime dateFrom;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime dateTo;
    @ManyToOne
    private Treatment treatment;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Salon salon;
    @ManyToOne(cascade = CascadeType.ALL)
   //@OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    @JoinColumn(name = "employee_id")

    private Employee employee;
    @ManyToOne(cascade = CascadeType.ALL)
   // @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
   @JoinColumn(name = "customer_id")
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