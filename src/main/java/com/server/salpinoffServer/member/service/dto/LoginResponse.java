package com.server.salpinoffServer.member.service.dto;

import java.time.LocalDateTime;

public record LoginResponse(Long memberId, String accessToken, String refreshToken,
                            String username, LocalDateTime createdAt, int code) {
}
