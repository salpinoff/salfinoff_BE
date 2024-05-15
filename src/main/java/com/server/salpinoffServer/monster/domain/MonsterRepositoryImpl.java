package com.server.salpinoffServer.monster.domain;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.server.salpinoffServer.infra.exception.NotFoundException;
import com.server.salpinoffServer.monster.service.MonsterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Objects;

import static com.server.salpinoffServer.monster.domain.QMonster.*;

@Repository
@RequiredArgsConstructor
public class MonsterRepositoryImpl implements MonsterRepository {

    private final JPAQueryFactory queryFactory;
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

    @Override
    public Monster getLatestMonsterByMember(Long memberId) {
        Monster latestMonster = queryFactory
                .select(monster)
                .from(monster)
                .where(monster.memberId.eq(memberId))
                .orderBy(monster.id.desc())
                .fetchFirst();

        if (Objects.isNull(latestMonster)) {
            throw new NotFoundException("해당 유저의 몬스터가 존재하지 않습니다.");
        }
        return latestMonster;
    }
}
