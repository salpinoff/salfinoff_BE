package com.server.salpinoffServer.monster.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class EncouragementMessageRequest {

    private String sender;

    @NotBlank
    private String content;
}
