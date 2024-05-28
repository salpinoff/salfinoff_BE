package com.server.salpinoffServer.infra.exception.ui.dto;

public record ExceptionResponse(String name, String message) {

    public String convertToJson() {
        return "{" +
                "\"code\": " + "\"" + name + "\",\n" +
                "\"message\": " + "\"" + message + "\"\n" +
                "}";
    }
}
