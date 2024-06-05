package com.server.salpinoffServer.monster.domain;

import com.server.salpinoffServer.infra.entity.BaseEntity;
import com.server.salpinoffServer.monster.service.dto.MonsterCreationRequest;
import com.server.salpinoffServer.monster.service.dto.MonsterDecorationResponse;
import com.server.salpinoffServer.monster.service.dto.MonsterModificationRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.access.AccessDeniedException;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Monster extends BaseEntity {

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false, length = 50)
    private String monsterName;

    @Column(nullable = false)
    private int rating;

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
    public Monster(Long memberId, String monsterName, int rating, Emotion emotion, String content) {
        this.memberId = memberId;
        this.monsterName = monsterName;
        this.rating = rating;
        this.interactionCount = RatingRange.getRatingRangeByRating(rating).getTotalInteractionCount();
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
                .rating(request.getRating())
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

    public void update(Long memberId, MonsterModificationRequest request) {
        if (!isOwner(memberId)) {
            throw new AccessDeniedException("몬스터 수정 권한이 없습니다.");
        }

        this.content = request.getContent();
    }

    public void encourage(Long memberId) {
        this.encourage(memberId, RatingRange.getRatingRangeByRating(this.rating).getInteractionCountPerEncouragement());
    }

    public void encourage(Long memberId, int count) {
        if (!isOwner(memberId)) {
            throw new AccessDeniedException("몬스터에게 인터렉션 할 수 있는 권한이 없습니다.");
        }

        int totalInteractionCount = this.currentInteractionCount + count;

        this.currentInteractionCount = this.interactionCount <= totalInteractionCount ?
                this.interactionCount : totalInteractionCount;
    }

    public enum Emotion {
        DEPRESSION,
        ANGER
    }

    @Getter
    public enum RatingRange {
        RANGE_1(1, 25, 50, 10),
        RANGE_2(26, 50, 100, 20),
        RANGE_3(51, 75, 150, 30),
        RANGE_4(76, 100, 200, 40);

        private final int minRating;
        private final int maxRating;
        private final int totalInteractionCount;
        private final int interactionCountPerEncouragement;

        RatingRange(int minRating, int maxRating, int totalInteractionCount, int interactionCountPerEncouragement) {
            this.minRating = minRating;
            this.maxRating = maxRating;
            this.totalInteractionCount = totalInteractionCount;
            this.interactionCountPerEncouragement = interactionCountPerEncouragement;
        }

        public static RatingRange getRatingRangeByRating(int rating) {
            return Arrays.stream(values()).filter(v -> v.minRating <= rating && v.maxRating >= rating)
                    .findAny().orElseThrow(() -> new IllegalArgumentException("1 ~ 100 사이의 수치만 가능합니다."));
        }
    }

}
