package com.server.salpinoffServer.monster.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MonsterCreationRequest {

    @NotBlank
    private String monsterName;

    @NotNull
    private int interactionCount;

    @NotNull
    private String emotion; // enum 으로 변경하기

    @NotEmpty
    private String content;

    @NotNull
    private MonsterDecorationRequest monsterDecoration;

}
