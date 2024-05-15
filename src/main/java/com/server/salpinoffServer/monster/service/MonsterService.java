package com.server.salpinoffServer.monster.service;

import com.server.salpinoffServer.infra.auth.dto.MemberInfo;
import com.server.salpinoffServer.infra.exception.NotFoundException;
import com.server.salpinoffServer.monster.domain.Monster;
import com.server.salpinoffServer.monster.domain.MonsterDecoration;
import com.server.salpinoffServer.monster.service.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MonsterService {

    private final MonsterRepository monsterRepository;

    @Transactional
    public MonsterInteractionResponse interactMonster(Long monsterId, Long memberId, MonsterInteractionRequest request) {
        return null;
    }

    @Transactional(readOnly = true)
    public Page<MonsterMessagesResponse> getMonsterMessages(Long monsterId, Long memberId, Pageable pageable) {
        return new PageImpl<>(List.of(), pageable, 0L);
    }

    @Transactional(readOnly = true)
    public MonsterDetailsResponse getMonster(@Nullable MemberInfo memberInfo, Long monsterId) {

        Monster monster = monsterRepository.getMonster(monsterId);

        if (Objects.nonNull(memberInfo) && monster.isOwner(memberInfo.memberId())) {
            return MonsterDetailsResponse.of(monster);
        }

        if (monster.isFreedom()) {
            throw new NotFoundException("자유를 찾아 떠나가 버린 몬스터입니다.");
        }
        return MonsterDetailsResponse.of(monster);
    }

    public Page<MonsterDetailsResponse> getMonstersByMember(Long memberId, Pageable pageable) {
        return monsterRepository.findMonstersByMember(memberId, pageable)
                .map(MonsterDetailsResponse::of);
    }

    public MonsterDetailsResponse getRepMonsterByMember(Long memberId) {
        return MonsterDetailsResponse.of(monsterRepository.getLatestMonsterByMember(memberId));
    }

    @Transactional
    public void create(Long memberId, MonsterCreationRequest request) {
        Monster monster = monsterRepository.saveMonster(Monster.from(memberId, request));

        List<MonsterDecoration> monsterDecorations = request.getMonsterDecorations().stream()
                .map(req -> new MonsterDecoration(monster.getId(), req.getDecorationType(), req.getDecorationValue()))
                .toList();

        monster.addDecorations(monsterDecorations);
    }
}
