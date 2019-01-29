package com.challenge.volcano.island.controllers.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ErrorDetails {
    private Date timestamp;
    private String error_code;
    private String error_message;
    private String path;

    public ErrorDetails(Date timestamp, String error_code, String error_message, String path) {
        this.timestamp = timestamp;
        this.error_code = error_code;
        this.error_message = error_message;
        this.path = path;
    }

}