package com.server.salpinoffServer.member.service;

import com.server.salpinoffServer.infra.auth.jwt.JwtManager;
import com.server.salpinoffServer.member.domain.Member;
import com.server.salpinoffServer.member.domain.Token;
import com.server.salpinoffServer.member.service.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

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

        return new LoginResponse(member.getId(), accessToken, refreshToken);
    }
}
