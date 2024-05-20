package com.server.salpinoffServer.member.service.dto;

import lombok.Getter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Getter
public class KakaoTokenResponse {
    private String id_token;

    public KakaoTokenResponse() {
    }

    public KakaoTokenResponse(String id_token) {
        this.id_token = id_token;
    }

    public MultiValueMap<String, String> toMultiValueMap() {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("id_token", id_token);
        return multiValueMap;
    }
}
