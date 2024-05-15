package com.server.salpinoffServer.monster.domain;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.server.salpinoffServer.infra.exception.NotFoundException;
import com.server.salpinoffServer.monster.service.MonsterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.server.salpinoffServer.monster.domain.QMonster.monster;
import static com.server.salpinoffServer.monster.domain.QMonsterMessage.monsterMessage;

@Repository
@RequiredArgsConstructor
public class MonsterRepositoryImpl implements MonsterRepository {

    private final MonsterMessageJpaRepository monsterMessageJpaRepository;
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

    @Override
    public Page<Monster> findMonstersByMember(Long memberId, Pageable pageable) {
        List<Monster> monsters = queryFactory
                .select(monster)
                .from(monster)
                .where(monster.memberId.eq(memberId))
                .orderBy(monster.id.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(monster.count())
                .from(monster)
                .where(monster.memberId.eq(memberId));

        return PageableExecutionUtils.getPage(monsters, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<MonsterMessage> findMonsterMessages(Long monsterId, Pageable pageable) {
        List<MonsterMessage> monsterMessages = queryFactory
                .select(monsterMessage)
                .from(monsterMessage)
                .where(monsterMessage.monsterId.eq(monsterId))
                .orderBy(monsterMessage.checked.asc(), monsterMessage.id.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(monsterMessage.count())
                .from(monsterMessage)
                .where(monsterMessage.monsterId.eq(monsterId));

        return PageableExecutionUtils.getPage(monsterMessages, pageable, countQuery::fetchOne);
    }

    @Override
    public MonsterMessage getMonsterMessage(Long messageId) {
        return monsterMessageJpaRepository.findById(messageId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 응원 메시지 입니다."));
    }

    @Override
    public MonsterMessage saveMonsterMessage(MonsterMessage monsterMessage) {
        return monsterMessageJpaRepository.save(monsterMessage);
    }

    @Override
    public boolean existsMonsterByMember(Long memberId) {
        Monster m = queryFactory
                .select(monster)
                .from(monster)
                .where(monster.memberId.eq(memberId))
                .fetchOne();

        return m != null;
    }
}
