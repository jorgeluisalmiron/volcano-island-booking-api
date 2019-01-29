package com.challenge.volcano.island.controllers.response;

import lombok.Getter;

@Getter
public class OkResponse extends MessageResponse {
    public OkResponse(){
        super("000","Success");
    }
}
