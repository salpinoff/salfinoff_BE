package com.server.salpinoffServer.monster.service.dto;

import com.server.salpinoffServer.monster.domain.Monster;

import java.util.List;

/**
 * @param emotion enum 으로 변경
 */
public record MonsterDetailsResponse(Long monsterId, String monsterName, int interactionCount,
                                     int currentInteractionCount, Monster.Emotion emotion, String content,
                                     List<MonsterDecorationResponse> monsterDecorations) {

    public static MonsterDetailsResponse of(Monster monster) {
        return new MonsterDetailsResponse(monster.getId(), monster.getMonsterName(), monster.getInteractionCount(),
                monster.getCurrentInteractionCount(), monster.getEmotion(),monster.getContent(),
                monster.monsterDecorationResponses());
    }
}
