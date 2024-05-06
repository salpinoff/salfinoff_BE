package com.server.salpinoffServer.member.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MemberInfoRequest {

    @NotBlank
    private String username;
}
