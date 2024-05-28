package com.server.salpinoffServer.monster.service.dto;

import com.server.salpinoffServer.monster.domain.Monster;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;

import java.util.List;

@Getter
public class MonsterCreationRequest {

    @NotBlank
    private String monsterName;

    @Min(1)
    @Max(100)
    private int rating;

    @NotNull
    private Monster.Emotion emotion;

    @NotEmpty
    private String content;

    @NotNull
    @Valid
    private List<MonsterDecorationRequest> monsterDecorations;

}
