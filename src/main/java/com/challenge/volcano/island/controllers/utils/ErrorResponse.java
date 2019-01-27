package com.challenge.volcano.island.controllers.utils;

import com.challenge.volcano.island.controllers.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.stream.Collectors;

public class ErrorResponse {
    public static ResponseEntity<Object> generateError(BindingResult bindingResult) {
        MessageResponse messageResponse = new MessageResponse("400",
                bindingResult.getAllErrors()
                        .stream()
                        .map(x -> x.getDefaultMessage())
                        .collect(Collectors.joining(", ")));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
    }
}
