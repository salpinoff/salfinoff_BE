package com.server.salpinoffServer.monster.service.dto;

import lombok.Getter;

@Getter
public record MonsterInteractionResponse(Long monsterId, int currentInteractionCount) {
}
