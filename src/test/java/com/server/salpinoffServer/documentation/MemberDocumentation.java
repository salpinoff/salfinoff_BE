package com.server.salpinoffServer.documentation;

import com.server.salpinoffServer.member.service.MemberService;
import com.server.salpinoffServer.member.service.OAuthManager;
import com.server.salpinoffServer.member.service.dto.LoginResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

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
        LoginResponse loginResponse = new LoginResponse(1L, "accessToken", "refreshToken");

        //when
        when(oAuthManager.findSocialKeyByKakao(any())).thenReturn("socialKey");
        when(memberService.login("socialKey")).thenReturn(loginResponse);

        //then
        로그인_카카오(getRequestSpecification("login-kakao"), "code");
    }

    @Test
    void refreshToken() {
        //given
        LoginResponse loginResponse = new LoginResponse(1L, "accessToken", "refreshToken");

        //when
        when(memberService.refreshToken(any())).thenReturn(loginResponse);

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

}
