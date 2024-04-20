package com.server.salpinoffServer.member.service.dto;

import lombok.Getter;

@Getter
public class LoginResponse {

    private final Long memberId;
    private final String accessToken;
    private final String refreshToken;

    public LoginResponse(Long memberId, String accessToken, String refreshToken) {
        this.memberId = memberId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
