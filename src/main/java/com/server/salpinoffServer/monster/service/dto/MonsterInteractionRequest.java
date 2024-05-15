package com.server.salpinoffServer.monster.service.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;

@Getter
public class MonsterInteractionRequest {

    @Min(1)
    private int interactionCount;
}
