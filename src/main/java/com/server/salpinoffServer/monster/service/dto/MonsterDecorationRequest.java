package com.server.salpinoffServer.monster.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MonsterDecorationRequest {

    @NotBlank
    private String backgroundColor;
}
