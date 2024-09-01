package com.server.salpinoffServer.monster.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MonsterSearchCriteria {
    private Long memberId;
    private Long monsterId;
    private Monster.Status monsterStatus;

    @Builder
    public MonsterSearchCriteria(Long memberId, Long monsterId, Monster.Status monsterStatus) {
        this.memberId = memberId;
        this.monsterId = monsterId;
        this.monsterStatus = monsterStatus;
    }
}
