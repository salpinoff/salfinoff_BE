package com.server.salpinoffServer.monster.domain;

import com.server.salpinoffServer.infra.exception.NotFoundException;
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

    @Override
    public Monster getMonster(Long monsterId) {
        return monsterJpaRepository.findById(monsterId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 몬스터입니다."));
    }
}
