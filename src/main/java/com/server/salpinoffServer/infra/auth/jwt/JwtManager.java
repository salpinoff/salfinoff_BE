package com.server.salpinoffServer.infra.auth.jwt;

import org.springframework.stereotype.Component;

@Component
public class JwtManager {

    public String createRefreshToken() {
        return "refreshToken";
    }

    public String createAccessToken(Long memberId) {
        return "accessToken: "+ memberId;
    }
}