package com.server.salpinoffServer.monster.service.dto;

import com.server.salpinoffServer.monster.domain.MonsterDecoration;

public record MonsterDecorationResponse(Long decorationId, MonsterDecoration.Type decorationType,
                                        String decorationValue) {
    public static MonsterDecorationResponse of(MonsterDecoration monsterDecoration) {
        return new MonsterDecorationResponse(monsterDecoration.getId(), monsterDecoration.getDecorationType(),
                monsterDecoration.getDecorationValue());
    }
}
