package com.server.salpinoffServer.monster.service.dto;

import lombok.Getter;

@Getter
public class MonsterIdResponse {

    public Long monsterId;

    public MonsterIdResponse(Long monsterId) {
        this.monsterId = monsterId;
    }
}
