package com.challenge.volcano.island.controllers.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookingInfo {

    private LocalDate arrivalOn;
    private LocalDate departureOn;
    private int qtyPersons;

}
