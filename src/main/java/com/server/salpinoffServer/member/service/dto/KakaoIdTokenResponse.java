package com.server.salpinoffServer.member.service.dto;

import lombok.Getter;

@Getter
public class KakaoIdTokenResponse {
    private String sub;

    public KakaoIdTokenResponse() {
    }

    public KakaoIdTokenResponse(String sub) {
        this.sub = sub;
    }
}
