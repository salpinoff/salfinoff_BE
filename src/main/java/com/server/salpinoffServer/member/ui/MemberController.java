package com.server.salpinoffServer.member.ui;

import com.server.salpinoffServer.member.service.MemberService;
import com.server.salpinoffServer.member.service.OAuthManager;
import com.server.salpinoffServer.member.service.dto.LoginKakaoRequest;
import com.server.salpinoffServer.member.service.dto.LoginResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {

    private final OAuthManager oAuthManager;
    private final MemberService memberService;

    @PostMapping("/login/kakao")
    public ResponseEntity<LoginResponse> loginKakao(@Valid @RequestBody LoginKakaoRequest request) {
        String socialKey = oAuthManager.findSocialKeyByKakao(request);
        LoginResponse loginResponse = memberService.login(socialKey);

        return ResponseEntity.ok(loginResponse);
    }
}
