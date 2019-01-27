package com.challenge.volcano.island.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ChangeAvailabilities {
    LocalDate arrivalOn;
    long days;
    int qtyPersons;
    boolean availChecked;

    public ChangeAvailabilities(LocalDate arrivalOn, long days, int qtyPersons) {
        this.arrivalOn = arrivalOn;
        this.days = days;
        this.qtyPersons = qtyPersons;
    }

    public ChangeAvailabilities() {
    }
}
