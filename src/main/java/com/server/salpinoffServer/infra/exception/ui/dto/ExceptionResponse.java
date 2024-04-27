package com.server.salpinoffServer.infra.exception.ui.dto;

import lombok.Getter;

@Getter
public class ExceptionResponse {

    private final String name;
    private final String message;

    public ExceptionResponse(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public String convertToJson() {
        return "{" +
                "\"code\": " + "\"" + name + "\",\n" +
                "\"message\": " + "\"" + message + "\"\n" +
                "}";
    }
}
