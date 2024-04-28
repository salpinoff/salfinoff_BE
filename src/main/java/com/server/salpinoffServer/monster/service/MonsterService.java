package com.server.salpinoffServer.monster.service;

import com.server.salpinoffServer.monster.service.dto.MonsterInteractionRequest;
import com.server.salpinoffServer.monster.service.dto.MonsterInteractionResponse;
import com.server.salpinoffServer.monster.service.dto.MonsterMessagesResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MonsterService {

    @Transactional
    public MonsterInteractionResponse interactMonster(Long monsterId, Long memberId, MonsterInteractionRequest request) {
        return new MonsterInteractionResponse();
    }

    @Transactional(readOnly = true)
    public Page<MonsterMessagesResponse> getMonsterMessages(Long monsterId, Long memberId, Pageable pageable) {
        return new PageImpl<>(List.of(), pageable, 0L);
    }

}
