package com.server.salpinoffServer.monster.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MonsterDecoration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
