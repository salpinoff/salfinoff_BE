package com.server.salpinoffServer.member.service;

import com.server.salpinoffServer.infra.auth.jwt.JwtManager;
import com.server.salpinoffServer.member.domain.Member;
import com.server.salpinoffServer.member.domain.Token;
import com.server.salpinoffServer.member.service.dto.LoginResponse;
import com.server.salpinoffServer.member.service.dto.MemberInfoRequest;
import com.server.salpinoffServer.member.service.dto.RefreshTokenRequest;
import com.server.salpinoffServer.monster.service.MonsterService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MonsterService monsterService;
    private final MemberRepository memberRepository;
    private final JwtManager jwtManager;

    @Transactional
    public LoginResponse login(String socialKey) {

        Member member = memberRepository.findMemberBySocialId(socialKey)
                .orElseGet(() -> memberRepository.saveMember(Member.toUser(socialKey)));

        String refreshToken = jwtManager.createRefreshToken(member);
        memberRepository.findTokenByMemberId(member.getId()).ifPresentOrElse(
                token -> token.changeRefreshToken(refreshToken),
                () -> memberRepository.saveToken(new Token(refreshToken, member.getId()))
        );

        String accessToken = jwtManager.createAccessToken(member);

        boolean hasMonster = monsterService.hasMonsterForMember(member.getId());

        return new LoginResponse(member.getId(), accessToken, refreshToken, member.getUsername(),
                member.signUpStatus(hasMonster).value());
    }

    @Transactional
    public LoginResponse refreshToken(RefreshTokenRequest request) {
        Token token = memberRepository.getTokenByRefreshToken(request.getRefreshToken());

        if (jwtManager.isExpired(token.getRefreshToken())) {
            throw new AccessDeniedException("토큰이 만료되었습니다. 재 로그인이 필요합니다.");
        }

        Member member = memberRepository.getMember(token.getMemberId());
        String accessToken = jwtManager.createAccessToken(member);

        String refreshToken = jwtManager.createRefreshToken(member);
        token.changeRefreshToken(refreshToken);

        boolean hasMonster = monsterService.hasMonsterForMember(member.getId());

        return new LoginResponse(member.getId(), accessToken, refreshToken, member.getUsername(),
                member.signUpStatus(hasMonster).value());
    }

    @Transactional
    public void logout(Long memberId) {
        memberRepository.deleteTokenByMemberId(memberId);
    }

    @Transactional
    public void updateMemberInfo(Long memberId, MemberInfoRequest request) {
        memberRepository.getMember(memberId).update(request);
    }
}
