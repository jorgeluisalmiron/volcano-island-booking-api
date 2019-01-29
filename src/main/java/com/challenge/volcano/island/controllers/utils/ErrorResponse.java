package com.challenge.volcano.island.controllers.utils;

import com.challenge.volcano.island.controllers.response.MessageResponse;
import org.springframework.validation.BindingResult;

import java.util.stream.Collectors;

public class ErrorResponse {
    public static MessageResponse generateError(BindingResult bindingResult) {
        MessageResponse messageResponse = new MessageResponse("001", "Request errors: " +
                bindingResult.getAllErrors()
                        .stream()
                        .map(x -> x.getDefaultMessage())
                        .collect(Collectors.joining(", ")));
        return messageResponse;
    }
}
