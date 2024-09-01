package com.server.salpinoffServer.monster.service.dto;

import com.server.salpinoffServer.monster.domain.Monster;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MonsterByMemberRequest {
    private Monster.Status monsterStatus = Monster.Status.FREEDOM;
}
