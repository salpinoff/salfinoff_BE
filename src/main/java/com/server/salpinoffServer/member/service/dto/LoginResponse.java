package com.server.salpinoffServer.member.service.dto;

public record LoginResponse(Long memberId, String accessToken, String refreshToken,
                            String username, int code) {
}
