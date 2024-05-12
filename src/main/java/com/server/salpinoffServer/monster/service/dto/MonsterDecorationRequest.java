package com.server.salpinoffServer.monster.service.dto;

import com.server.salpinoffServer.monster.domain.MonsterDecoration;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MonsterDecorationRequest {

    @NotNull
    private MonsterDecoration.Type type;

    @NotBlank
    private String value;
}
