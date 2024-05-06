package com.server.salpinoffServer.monster.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MonsterModificationRequest {

    @NotBlank
    private String content;
}
