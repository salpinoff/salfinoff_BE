package com.server.salpinoffServer.member.service.dto;

public record TokenResponse(Long memberId, String accessToken, String refreshToken) {
}
