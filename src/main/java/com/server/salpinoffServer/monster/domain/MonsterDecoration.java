package com.server.salpinoffServer.monster.domain;

import com.server.salpinoffServer.infra.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MonsterDecoration extends BaseEntity {

    @Column(nullable = false)
    private Long monsterId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type decorationType;

    @Column(nullable = false)
    private String decorationValue;

    public MonsterDecoration(Long monsterId, Type decorationType, String decorationValue) {
        this.monsterId = monsterId;
        this.decorationType = decorationType;
        this.decorationValue = decorationValue;
    }

    public enum Type {
        BACKGROUND_COLOR
    }
}
