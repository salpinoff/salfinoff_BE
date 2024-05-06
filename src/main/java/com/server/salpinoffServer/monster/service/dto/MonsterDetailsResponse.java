package com.server.salpinoffServer.monster.service.dto;

/**
 * @param emotion enum 으로 변경
 */
public record MonsterDetailsResponse(Long monsterId, String monsterName, int interactionCount,
                                     int currentInteractionCount, String emotion, String content,
                                     MonsterDecorationResponse monsterDecoration) {

}
