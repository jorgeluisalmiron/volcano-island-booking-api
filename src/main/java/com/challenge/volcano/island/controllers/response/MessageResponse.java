package com.challenge.volcano.island.controllers.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class MessageResponse {
    private String code;
    private String message;

    public MessageResponse(String code, String message) {
        this.code = code;
        this.message = message;

    }
}
