package com.server.salpinoffServer.member.service.dto;

import lombok.Getter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Getter
public class KakaoTokenRequest {

    private final String grant_type = "authorization_code";
    private final String client_id;
    private final String redirect_uri;
    private final String code;

    public KakaoTokenRequest(String client_id, String redirect_uri, String code) {
        this.client_id = client_id;
        this.redirect_uri = redirect_uri;
        this.code = code;
    }

    public MultiValueMap<String, String> toMultiValueMap() {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("grant_type", this.grant_type);
        multiValueMap.add("client_id", this.client_id);
        multiValueMap.add("redirect_uri", this.redirect_uri);
        multiValueMap.add("code", this.code);
        return multiValueMap;
    }
}
