package com.challenge.volcano.island.controllers.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class BookingInfo {
    @NotNull(message = "arrivalOn: is required")
    @Future(message = "arrivalOn: requires a future day")
    private LocalDate arrivalOn;
    @NotNull(message = "departureOn: is required")
    @Future(message = "departureOn: requires a future day")
    private LocalDate departureOn;
    @NotNull(message = "qtyPersons: is required")
    @Min(value = 1, message = "qtyPersons: min value is 1")
    private int qtyPersons;

}
