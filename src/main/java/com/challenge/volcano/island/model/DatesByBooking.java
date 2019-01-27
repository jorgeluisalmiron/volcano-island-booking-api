package com.challenge.volcano.island.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "dates_by_booking")
public class DatesByBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "dates_by_booking_generator")
    @SequenceGenerator(name="dates_by_booking_generator", sequenceName = "dates_by_booking_seq", initialValue = 100,
            allocationSize = 1)
    private Long id;
    @NotNull
    private LocalDate date;
    @ManyToOne
    private Booking booking;

    public DatesByBooking(@NotNull LocalDate date) {
        this.date = date;
    }

    public int getBookingQtyPersons(){
        return booking.getQtyPersons();
    }

    public DatesByBooking() {
    }
}
