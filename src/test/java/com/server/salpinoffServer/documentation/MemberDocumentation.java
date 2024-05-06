package com.server.salpinoffServer.documentation;

import com.server.salpinoffServer.member.service.MemberService;
import com.server.salpinoffServer.member.service.OAuthManager;
import com.server.salpinoffServer.member.service.dto.LoginResponse;
import com.server.salpinoffServer.member.service.dto.TokenResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Map;

import static com.server.salpinoffServer.member.acceptance.MemberSteps.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class MemberDocumentation extends Documentation {

    @MockBean
    private OAuthManager oAuthManager;

    @MockBean
    private MemberService memberService;

    @Test
    void loginKakao() {
        //given
        LoginResponse loginResponse = new LoginResponse(1L, "accessToken", "refreshToken",
                "빵빵이", 102);

        //when
        when(oAuthManager.findSocialKeyByKakao(any())).thenReturn("socialKey");
        when(memberService.login("socialKey")).thenReturn(loginResponse);

        //then
        로그인_카카오(getRequestSpecification("login-kakao"), "code");
    }

    @Test
    void refreshToken() {
        //given
        TokenResponse tokenResponse = new TokenResponse(1L, "accessToken", "refreshToken");

        //when
        when(memberService.refreshToken(any())).thenReturn(tokenResponse);

        //then
        토큰_재발급(getRequestSpecification("token-refresh"), "refreshToken");
    }

    @Test
    void logout() {
        //given
        setAccessToken();

        //then
        로그아웃(getRequestSpecification("logout").auth().oauth2("accessToken"));
    }

    @Test
    void registerMemberInfo() {
        //given
        setAccessToken();

        //when
        Map<String, Object> request = 회원정보_등록_요청값("빵빵이");

        //then
        회원정보_등록(getRequestSpecification("member-info-registration").auth().oauth2("accessToken"), request);
    }

    @Test
    void updateMemberInfo() {
        //given
        setAccessToken();

        //when
        Map<String, Object> request = 회원정보_수정_요청값("옥지");

        //then
        회원정보_수정(getRequestSpecification("member-info-modification").auth().oauth2("accessToken"), request);
    }
}
