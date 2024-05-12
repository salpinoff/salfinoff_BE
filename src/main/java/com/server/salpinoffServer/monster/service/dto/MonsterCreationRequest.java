package com.server.salpinoffServer.monster.service.dto;

import com.server.salpinoffServer.monster.domain.Monster;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class MonsterCreationRequest {

    @NotBlank
    private String monsterName;

    @NotNull
    private int interactionCount;

    @NotNull
    private Monster.Emotion emotion;

    @NotEmpty
    private String content;

    @NotNull
    @Valid
    private List<MonsterDecorationRequest> monsterDecorations;

}
