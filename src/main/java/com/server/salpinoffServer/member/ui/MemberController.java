package com.server.salpinoffServer.member.ui;

import com.server.salpinoffServer.infra.auth.dto.UserInfo;
import com.server.salpinoffServer.member.service.MemberService;
import com.server.salpinoffServer.member.service.OAuthManager;
import com.server.salpinoffServer.member.service.dto.LoginKakaoRequest;
import com.server.salpinoffServer.member.service.dto.LoginResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/my")
    public ResponseEntity<Void> getMyInfo(@AuthenticationPrincipal UserInfo userInfo) {

        System.out.println(userInfo.getUserId());

        return ResponseEntity.ok().build();
    }
}
