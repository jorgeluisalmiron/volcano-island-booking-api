package com.challenge.volcano.island.exceptions;

import lombok.Getter;

@Getter
public class NoAvailabilityException extends Exception {
    private static final String NO_AVAILABILITY = "No Availability";
    private String code;
    private String message;

    public NoAvailabilityException() {
        super(NO_AVAILABILITY);
        this.code = "406";
        this.message = NO_AVAILABILITY;
    }
}
