package com.server.salpinoffServer.monster.service.dto;

import lombok.Getter;

@Getter
public class MonsterInteractionResponse {
    private Long monsterId;
    private int remainingInteractionCount;

    public MonsterInteractionResponse() {
    }

    public MonsterInteractionResponse(Long monsterId, int remainingInteractionCount) {
        this.monsterId = monsterId;
        this.remainingInteractionCount = remainingInteractionCount;
    }
}
