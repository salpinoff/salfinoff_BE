package com.server.salpinoffServer.member.service;

import com.server.salpinoffServer.member.domain.Member;
import com.server.salpinoffServer.member.domain.Token;

import java.util.Optional;

public interface MemberRepository {

    Optional<Member> findMemberBySocialId(String socialKey);

    Token getTokenByMemberId(Long memberId);

    Optional<Token> findTokenByMemberId(Long memberId);

    Member saveMember(Member member);

    Token saveToken(Token token);
}
