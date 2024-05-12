package com.server.salpinoffServer.monster.domain;

import com.server.salpinoffServer.monster.service.MonsterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MonsterRepositoryImpl implements MonsterRepository {

    private final MonsterJpaRepository monsterJpaRepository;

    @Override
    public Monster saveMonster(Monster monster) {
        return monsterJpaRepository.save(monster);
    }
}
