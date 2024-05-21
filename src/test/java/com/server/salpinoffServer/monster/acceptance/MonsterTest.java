package com.server.salpinoffServer.monster.acceptance;

import com.server.salpinoffServer.monster.domain.Monster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MonsterTest {

    Monster monster;

    @BeforeEach
    void setUp() {
        monster = new Monster(1L, "축느러지미", Monster.RatingRange.RANGE_1.getMaxRating(), Monster.Emotion.DEPRESSION,
                "축 축 느러진다. 프로젝트 일정이 축축 늘어진다.");
    }

    @Test
    void 몬스터_주인_여부_확인() {
        //then
        assertThat(monster.isOwner(1L)).isTrue();
        assertThat(monster.isOwner(2L)).isFalse();
    }

    @Test
    void 몬스터_생성_인터렉션_값과_현재_인터렉션_값이_크거나_같으면_자유상태() {
        //when
        monster.encourage(Monster.RatingRange.RANGE_1.getTotalInteractionCount());

        //then
        assertThat(monster.isFreedom()).isTrue();
    }

    @Test
    void 몬스터_생성_인터렉션_값과_현재_인터렉션_값이_작으면_비자유상태() {
        //when
        monster.encourage(Monster.RatingRange.RANGE_1.getTotalInteractionCount() - 1);

        //then
        assertThat(monster.isFreedom()).isFalse();
    }

    @Test
    void 몬스터가_정해진_레이팅_만큼의_응원을_받으면_자유상태() {
        //when
        int 몬스터가_자유상태가_되기_위해_받아야_하는_응원_횟수 = Monster.RatingRange.RANGE_1.getTotalInteractionCount() /
                Monster.RatingRange.RANGE_1.getInteractionCountPerEncouragement();

        for (int i = 0; i < 몬스터가_자유상태가_되기_위해_받아야_하는_응원_횟수; i++) {
            monster.encourage();
        }

        //then
        assertThat(monster.isFreedom()).isTrue();
    }

    @Test
    void 몬스터가_정해진_레이팅_만큼의_응원을_받지_못했으면_비자유상태() {
        //when
        int 몬스터가_자유상태가_되기_위해_받아야_하는_응원_횟수 = Monster.RatingRange.RANGE_1.getTotalInteractionCount() /
                Monster.RatingRange.RANGE_1.getInteractionCountPerEncouragement();

        for (int i = 0; i < 몬스터가_자유상태가_되기_위해_받아야_하는_응원_횟수 - 1; i++) {
            monster.encourage();
        }

        //then
        assertThat(monster.isFreedom()).isFalse();
    }
}
