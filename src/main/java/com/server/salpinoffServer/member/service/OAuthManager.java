package com.server.salpinoffServer.member.service;

import com.server.salpinoffServer.member.service.dto.KakaoIdTokenResponse;
import com.server.salpinoffServer.member.service.dto.KakaoTokenRequest;
import com.server.salpinoffServer.member.service.dto.KakaoTokenResponse;
import com.server.salpinoffServer.member.service.dto.LoginKakaoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class OAuthManager {

    private final RestTemplate restTemplate;

    @Value("${props.oauth.kakao.token_uri}")
    private String kakaoTokenUri;

    @Value("${props.oauth.kakao.id_token_uri}")
    private String kakaoIdTokenUri;

    @Value("${props.oauth.kakao.client_id}")
    private String kakaoClientId;

    @Value("${props.oauth.kakao.redirect_uri}")
    private String kakaoRedirectUri;

    public String findSocialKeyByKakao(LoginKakaoRequest request) {

        KakaoTokenRequest kakaoTokenRequest = new KakaoTokenRequest(kakaoClientId, kakaoRedirectUri, request.getCode());

        KakaoTokenResponse kakaoTokenResponse =
                createHttpEntity(kakaoTokenUri, kakaoTokenRequest.toMultiValueMap(), KakaoTokenResponse.class);

        KakaoIdTokenResponse kakaoIdTokenResponse =
                createHttpEntity(kakaoIdTokenUri, kakaoTokenResponse.toMultiValueMap(), KakaoIdTokenResponse.class);

        return kakaoIdTokenResponse.getSub();
    }

    private <T> T createHttpEntity(String uri, MultiValueMap<String, String> requestBody, Class<T> responseType) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("charset", "utf-8");

        HttpEntity<MultiValueMap<String, String>> entity =
                new HttpEntity<>(Objects.requireNonNull(requestBody), headers);

        return Objects.requireNonNull(restTemplate
                .postForEntity(uri, entity, responseType)
                .getBody());
    }

}
