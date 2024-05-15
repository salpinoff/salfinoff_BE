package com.server.salpinoffServer.member.domain;

import com.server.salpinoffServer.infra.auth.jwt.JwtManager;
import com.server.salpinoffServer.infra.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Token extends BaseEntity {

    @Column(unique = true)
    private String refreshToken;

    @Column(unique = true)
    private Long memberId;

    public Token(String refreshToken, Long memberId) {
        this.refreshToken = refreshToken;
        this.memberId = memberId;
    }

    public void changeRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public boolean isExpired(JwtManager jwtManager) {
        return jwtManager.isExpired(refreshToken);
    }
}
