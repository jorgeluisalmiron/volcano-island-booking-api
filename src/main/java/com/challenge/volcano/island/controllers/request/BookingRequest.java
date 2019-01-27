package com.challenge.volcano.island.controllers.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class BookingRequest extends BookingInfo {
    @NotNull
    @Valid
    private PersonalInformation personalInformation;
}
