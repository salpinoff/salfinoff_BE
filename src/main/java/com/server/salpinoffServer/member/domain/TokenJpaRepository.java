package com.server.salpinoffServer.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenJpaRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByMemberId(Long memberId);
}
