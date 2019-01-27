package com.challenge.volcano.island.exceptions;

import lombok.Getter;

@Getter
public class CustomException extends Exception {
    private String code;
    public CustomException(String code, String message){
        super(message);
        this.code = code;
    }
}
