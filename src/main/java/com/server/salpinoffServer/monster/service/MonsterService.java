package com.server.salpinoffServer.monster.service;

import com.server.salpinoffServer.infra.auth.dto.MemberInfo;
import com.server.salpinoffServer.infra.exception.NotFoundException;
import com.server.salpinoffServer.member.domain.Member;
import com.server.salpinoffServer.member.service.MemberRepository;
import com.server.salpinoffServer.monster.domain.Monster;
import com.server.salpinoffServer.monster.domain.MonsterDecoration;
import com.server.salpinoffServer.monster.domain.MonsterMessage;
import com.server.salpinoffServer.monster.service.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MonsterService {

    private final MonsterRepository monsterRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public MonsterInteractionResponse interactMonster(Long monsterId, Long memberId, MonsterInteractionRequest request) {
        Monster monster = monsterRepository.getMonster(monsterId);

        monster.encourage(memberId, request.getInteractionCount());

        return new MonsterInteractionResponse(monster.getId(), monster.getCurrentInteractionCount());
    }

    @Transactional(readOnly = true)
    public Page<MonsterMessagesResponse> getMonsterMessages(Long monsterId, Long memberId, Pageable pageable) {
        Monster monster = monsterRepository.getMonster(monsterId);

        if (!monster.isOwner(memberId)) {
            throw new AccessDeniedException("응원 메시지 조회 권한이 없습니다.");
        }

        return monsterRepository.findMonsterMessages(monsterId, pageable)
                .map(MonsterMessagesResponse::of);
    }

    @Transactional(readOnly = true)
    public MonsterDetailsResponse getMonster(@Nullable MemberInfo memberInfo, Long monsterId) {

        Monster monster = monsterRepository.getMonster(monsterId);

        Member member = memberRepository.getMember(monster.getMemberId());

        if (Objects.nonNull(memberInfo) && monster.isOwner(memberInfo.memberId())) {
            return MonsterDetailsResponse.from(monster, member.getUsername());
        }

        if (monster.isFreedom()) {
            throw new NotFoundException("자유를 찾아 떠나가 버린 몬스터입니다.");
        }
        return MonsterDetailsResponse.from(monster, member.getUsername());
    }

    @Transactional(readOnly = true)
    public Page<MonsterDetailsResponse> getMonstersByMember(MemberInfo memberInfo, Pageable pageable) {
        return monsterRepository.findMonstersByMember(memberInfo.memberId(), pageable)
                .map(monster -> MonsterDetailsResponse.from(monster, memberInfo.username()));
    }

    @Transactional(readOnly = true)
    public MonsterDetailsResponse getRepMonsterByMember(MemberInfo memberInfo) {
        return MonsterDetailsResponse
                .from(monsterRepository.getLatestMonsterByMember(memberInfo.memberId()), memberInfo.username());
    }

    @Transactional
    public MonsterDetailsResponse createMonster(MemberInfo memberInfo, MonsterCreationRequest request) {
        Monster monster = monsterRepository.saveMonster(Monster.from(memberInfo.memberId(), request));

        List<MonsterDecoration> monsterDecorations = request.getMonsterDecorations().stream()
                .map(req -> new MonsterDecoration(monster.getId(), req.getDecorationType(), req.getDecorationValue()))
                .toList();

        monster.addDecorations(monsterDecorations);

        return MonsterDetailsResponse.from(monster, memberInfo.username());
    }

    @Transactional
    public void updateMonster(Long memberId, Long monsterId, MonsterModificationRequest request) {
        Monster monster = monsterRepository.getMonster(monsterId);

        monster.update(memberId, request);
    }

    @Transactional
    public void checkMonsterMessage(Long monsterId, Long messageId, Long memberId) {
        MonsterMessage monsterMessage = monsterRepository.getMonsterMessage(messageId);
        Monster monster = monsterRepository.getMonster(monsterId);

        monsterMessage.check(monsterId);
        monster.encourage(memberId);
    }

    @Transactional
    public void createMonsterMessage(Long monsterId, EncouragementMessageRequest request) {
        Monster monster = monsterRepository.getMonster(monsterId);

        if (monster.isFreedom()) {
            throw new NotFoundException("자유를 찾아 떠나가 버린 몬스터입니다.");
        }

        monsterRepository.saveMonsterMessage(MonsterMessage.from(monsterId, request));
    }

    public boolean hasMonsterForMember(Long memberId) {
        return monsterRepository.existsMonsterByMember(memberId);
    }
}
