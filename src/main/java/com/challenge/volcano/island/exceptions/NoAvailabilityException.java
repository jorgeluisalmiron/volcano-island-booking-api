package com.challenge.volcano.island.exceptions;

import lombok.Getter;

@Getter
public class NoAvailabilityException extends CustomException {
    public NoAvailabilityException(){
        super("901","No Availability");
    }
}
