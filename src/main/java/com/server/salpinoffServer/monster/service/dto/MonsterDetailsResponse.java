package com.server.salpinoffServer.monster.service.dto;

import com.server.salpinoffServer.monster.domain.Monster;

import java.time.LocalDateTime;
import java.util.List;

public record MonsterDetailsResponse(Long monsterId, String monsterName, String ownerName, int interactionCount,
                                     int currentInteractionCount, int interactionCountPerEncouragement,
                                     Monster.RatingRange ratingRange, Monster.Emotion emotion, String content,
                                     LocalDateTime createdAt, List<MonsterDecorationResponse> monsterDecorations) {

    public static MonsterDetailsResponse from(Monster monster, String ownerName) {
        return new MonsterDetailsResponse(monster.getId(), monster.getMonsterName(), ownerName,
                monster.getInteractionCount(), monster.getCurrentInteractionCount(),
                monster.getInteractionCountPerEncouragement(),
                Monster.RatingRange.getRatingRangeByRating(monster.getRating()),
                monster.getEmotion(), monster.getContent(), monster.getCreatedAt(),
                monster.monsterDecorationResponses());
    }
}
