package com.challenge.volcano.island.exceptions;

import lombok.Getter;

@Getter
public class RuleException extends CustomException{
    public RuleException(String code, String message){
        super(code, message);
    }
}
