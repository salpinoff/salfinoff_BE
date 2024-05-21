package com.server.salpinoffServer.monster.acceptance;

import com.server.salpinoffServer.monster.domain.Monster;
import com.server.salpinoffServer.monster.service.dto.MonsterModificationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.access.AccessDeniedException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
        monster.encourage(1L, Monster.RatingRange.RANGE_1.getTotalInteractionCount());

        //then
        assertThat(monster.isFreedom()).isTrue();
    }

    @Test
    void 몬스터_생성_인터렉션_값과_현재_인터렉션_값이_작으면_비자유상태() {
        //when
        monster.encourage(1L, Monster.RatingRange.RANGE_1.getTotalInteractionCount() - 1);

        //then
        assertThat(monster.isFreedom()).isFalse();
    }

    @Test
    void 몬스터가_정해진_레이팅_만큼의_응원을_받으면_자유상태() {
        //when
        int 몬스터가_자유상태가_되기_위해_받아야_하는_응원_횟수 = Monster.RatingRange.RANGE_1.getTotalInteractionCount() /
                Monster.RatingRange.RANGE_1.getInteractionCountPerEncouragement();

        for (int i = 0; i < 몬스터가_자유상태가_되기_위해_받아야_하는_응원_횟수; i++) {
            monster.encourage(1L);
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
            monster.encourage(1L);
        }

        //then
        assertThat(monster.isFreedom()).isFalse();
    }

    @Test
    void 몬스터_주인이_아닌_다른_회원이_인터렉션을_하면_예외처리() {
        //then
        assertThatThrownBy(() -> monster.encourage(2L, 59))
                .isInstanceOf(AccessDeniedException.class);
    }

    @Test
    void 몬스터_주인이_아닌_다른_회원이_웅원하면_예외처리() {
        //then
        assertThatThrownBy(() -> monster.encourage(2L))
                .isInstanceOf(AccessDeniedException.class);
    }

    @Test
    void 몬스터_수정을_주인이_아닌_다른_회원이_하면_예외처리() {
        //then
        assertThatThrownBy(() -> monster.update(2L, new MonsterModificationRequest()))
                .isInstanceOf(AccessDeniedException.class);
    }
}
