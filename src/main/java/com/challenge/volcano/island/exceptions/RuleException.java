package com.challenge.volcano.island.exceptions;

import lombok.Getter;

@Getter
public class RuleException extends Exception {
    private String code;
    private String message;
    public RuleException(String code, String message){
        super(message);
        this.code = code;
        this.message = message;
    }
}
