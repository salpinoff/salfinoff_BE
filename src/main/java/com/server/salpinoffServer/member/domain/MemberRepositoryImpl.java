package com.server.salpinoffServer.member.domain;

import com.server.salpinoffServer.infra.exception.NotFoundException;
import com.server.salpinoffServer.member.service.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    private final TokenJpaRepository tokenJpaRepository;

    @Override
    public Optional<Member> findMemberBySocialId(String socialKey) {
        return memberJpaRepository.findBySocialKey(socialKey);
    }

    @Override
    public Token getTokenByMemberId(Long memberId) {
        return tokenJpaRepository.findByMemberId(memberId)
                .orElseThrow(() -> new NotFoundException("토큰을 찾지 못했습니다."));
    }

    @Override
    public Optional<Token> findTokenByMemberId(Long memberId) {
        return tokenJpaRepository.findByMemberId(memberId);
    }

    @Override
    public Member saveMember(Member member) {
        return memberJpaRepository.save(member);
    }

    @Override
    public Token saveToken(Token token) {
        return tokenJpaRepository.save(token);
    }

    @Override
    public Token getTokenByRefreshToken(String refreshToken) {
        return tokenJpaRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 리프레시 토큰입니다."));
    }

    @Override
    public Member getMember(Long memberId) {
        return memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 회원입니다."));
    }
}
