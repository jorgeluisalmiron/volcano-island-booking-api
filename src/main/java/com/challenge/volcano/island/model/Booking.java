package com.challenge.volcano.island.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Getter
@Setter
@Entity
@Table(name = "bookings")
@Where(clause="status='booked'")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "booking_generator")
    @SequenceGenerator(name="booking_generator", sequenceName = "booking_seq", initialValue = 100, allocationSize = 1)
    private Long id;
    @NotNull
    @ManyToOne
    @JoinColumn(name="client_id")
    private Customer customer;
    @NotNull
    @Column(name = "arrival_on")
    private LocalDate arrivalOn;
    @NotNull
    @Column(name = "departure_on")
    private LocalDate departureOn;
    @NotNull
    @Column(name = "qty_persons")
    private int qtyPersons;
    @Column(name = "status")
    private String status;
    @JsonIgnore
    @OneToMany(fetch=FetchType.EAGER, cascade=ALL, orphanRemoval=true)
    @JoinColumn(name="booking_id")
    private List<DatesByBooking> datesByBookings = new ArrayList<>();
}
