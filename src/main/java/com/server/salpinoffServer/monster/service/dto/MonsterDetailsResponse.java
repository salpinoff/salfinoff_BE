package com.server.salpinoffServer.monster.service.dto;

import com.server.salpinoffServer.monster.domain.Monster;

import java.util.List;

public record MonsterDetailsResponse(Long monsterId, String monsterName, String ownerName, int interactionCount,
                                     int currentInteractionCount, Monster.Emotion emotion, String content,
                                     List<MonsterDecorationResponse> monsterDecorations) {

    public static MonsterDetailsResponse from(Monster monster, String ownerName) {
        return new MonsterDetailsResponse(monster.getId(), monster.getMonsterName(), ownerName,
                monster.getInteractionCount(), monster.getCurrentInteractionCount(), monster.getEmotion(),
                monster.getContent(), monster.monsterDecorationResponses());
    }
}
