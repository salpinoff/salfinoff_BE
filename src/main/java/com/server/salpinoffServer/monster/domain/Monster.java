package com.server.salpinoffServer.monster.domain;

import com.server.salpinoffServer.monster.service.dto.MonsterCreationRequest;
import com.server.salpinoffServer.monster.service.dto.MonsterDecorationResponse;
import com.server.salpinoffServer.monster.service.dto.MonsterModificationRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Monster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false, length = 50)
    private String monsterName;

    @Column(nullable = false)
    private int interactionCount;

    @Column(nullable = false)
    private int currentInteractionCount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Emotion emotion;

    @Column(nullable = false, length = 1000)
    private String content;

    @Embedded
    private MonsterDecorations monsterDecorations = new MonsterDecorations();

    @Builder
    private Monster(Long memberId, String monsterName, int interactionCount, Emotion emotion, String content) {
        this.memberId = memberId;
        this.monsterName = monsterName;
        this.interactionCount = interactionCount;
        this.currentInteractionCount = 0;
        this.emotion = emotion;
        this.content = content;
    }

    public static Monster from(Long memberId, MonsterCreationRequest request) {
        return Monster.builder()
                .memberId(memberId)
                .content(request.getContent())
                .monsterName(request.getMonsterName())
                .emotion(request.getEmotion())
                .interactionCount(request.getInteractionCount())
                .build();
    }

    public void addDecorations(List<MonsterDecoration> newMonsterDecorations) {
        monsterDecorations.addAll(newMonsterDecorations);
    }

    public boolean isOwner(Long memberId) {
        return Objects.equals(this.memberId, memberId);
    }

    public List<MonsterDecorationResponse> monsterDecorationResponses() {
        return monsterDecorations.monsterDecorationResponses();
    }

    public boolean isFreedom() {
        return interactionCount <= currentInteractionCount;
    }

    public void update(MonsterModificationRequest request) {
        this.content = request.getContent();
    }

    public enum Emotion {
        DEPRESSION
    }
}
