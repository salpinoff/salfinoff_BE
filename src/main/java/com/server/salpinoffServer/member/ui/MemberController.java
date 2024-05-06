package com.server.salpinoffServer.member.ui;

import com.server.salpinoffServer.infra.auth.dto.MemberInfo;
import com.server.salpinoffServer.member.service.MemberService;
import com.server.salpinoffServer.member.service.OAuthManager;
import com.server.salpinoffServer.member.service.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/members")
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

    @PostMapping("/token/refresh")
    public ResponseEntity<TokenResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        TokenResponse response = memberService.refreshToken(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal MemberInfo memberInfo) {
        memberService.logout(memberInfo.memberId());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/my")
    public ResponseEntity<Void> getMemberInfo(@AuthenticationPrincipal MemberInfo memberInfo) {

        System.out.println(memberInfo.memberId());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/my")
    public ResponseEntity<Void> registerMemberInfo(@AuthenticationPrincipal MemberInfo memberInfo,
                                                   @RequestBody @Valid MemberInfoRequest request) {
        memberService.updateMemberInfo(memberInfo.memberId(), request);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/my")
    public ResponseEntity<Void> updateMemberInfo(@AuthenticationPrincipal MemberInfo memberInfo,
                                                 @RequestBody @Valid MemberInfoRequest request) {
        memberService.updateMemberInfo(memberInfo.memberId(), request);

        return ResponseEntity.ok().build();
    }
}
