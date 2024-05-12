package com.server.salpinoffServer.monster.service;

import com.server.salpinoffServer.monster.domain.Monster;
import com.server.salpinoffServer.monster.domain.MonsterDecoration;
import com.server.salpinoffServer.monster.service.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    public MonsterDetailsResponse getMonster(Long monsterId) {
        return null;
    }

    public Page<MonsterDetailsResponse> getMonstersByMember(Long memberId, Pageable pageable) {
        return null;
    }

    public MonsterDetailsResponse getRepMonsterByMember(Long memberId) {
        return null;
    }

    @Transactional
    public void create(Long memberId, MonsterCreationRequest request) {
        Monster monster = monsterRepository.saveMonster(Monster.from(memberId, request));

        List<MonsterDecoration> monsterDecorations = request.getMonsterDecorations().stream()
                .map(req -> new MonsterDecoration(monster.getId(), req.getType(), req.getValue())).toList();

        monster.addDecorations(monsterDecorations);
    }
}
