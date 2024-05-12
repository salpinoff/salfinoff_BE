package com.server.salpinoffServer.monster.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;

import java.util.LinkedList;
import java.util.List;

@Embeddable
public class MonsterDecorations {

    @OneToMany(mappedBy = "monsterId", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private final List<MonsterDecoration> monsterDecorations = new LinkedList<>();

    public void addAll(List<MonsterDecoration> newMonsterDecorations) {
        monsterDecorations.addAll(newMonsterDecorations);
    }
}
