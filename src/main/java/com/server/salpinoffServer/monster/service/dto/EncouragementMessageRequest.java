package com.server.salpinoffServer.monster.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class EncouragementMessageRequest {

    @NotBlank
    private String sender;

    @NotBlank
    private String content;
}
