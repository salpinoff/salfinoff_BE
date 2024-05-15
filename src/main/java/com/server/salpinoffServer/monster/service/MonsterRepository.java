package com.server.salpinoffServer.monster.service;

import com.server.salpinoffServer.monster.domain.Monster;
import com.server.salpinoffServer.monster.domain.MonsterMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MonsterRepository {

    Monster saveMonster(Monster monster);

    Monster getMonster(Long monsterId);

    Monster getLatestMonsterByMember(Long memberId);

    Page<Monster> findMonstersByMember(Long memberId, Pageable pageable);

    Page<MonsterMessage> findMonsterMessages(Long monsterId, Pageable pageable);

    MonsterMessage getMonsterMessage(Long messageId);
}
