package com.server.salpinoffServer.monster.acceptance;

import com.server.salpinoffServer.monster.domain.Monster;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MonsterRatingRangeTest {

    @Test
    void rating_이_0_이하이면_예외처리() {
        //then
        assertThatThrownBy(() -> Monster.RatingRange.getRatingRangeByRating(0))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rating_이_1_이상_25이하이면_RANGE_1_을_반환() {
        //when
        Monster.RatingRange ratingRange1 = Monster.RatingRange.getRatingRangeByRating(1);

        //then
        assertThat(ratingRange1).isEqualTo(Monster.RatingRange.RANGE_1);

        //when
        Monster.RatingRange ratingRange25 = Monster.RatingRange.getRatingRangeByRating(25);

        //then
        assertThat(ratingRange25).isEqualTo(Monster.RatingRange.RANGE_1);
    }

    @Test
    void rating_이_26_이상_50이하이면_RANGE_2_을_반환() {
        //when
        Monster.RatingRange ratingRange26 = Monster.RatingRange.getRatingRangeByRating(26);

        //then
        assertThat(ratingRange26).isEqualTo(Monster.RatingRange.RANGE_2);

        //when
        Monster.RatingRange ratingRange50 = Monster.RatingRange.getRatingRangeByRating(50);

        //then
        assertThat(ratingRange50).isEqualTo(Monster.RatingRange.RANGE_2);
    }

    @Test
    void rating_이_51_이상_75이하이면_RANGE_3_을_반환() {
        //when
        Monster.RatingRange ratingRange51 = Monster.RatingRange.getRatingRangeByRating(51);

        //then
        assertThat(ratingRange51).isEqualTo(Monster.RatingRange.RANGE_3);

        //when
        Monster.RatingRange ratingRange75 = Monster.RatingRange.getRatingRangeByRating(75);

        //then
        assertThat(ratingRange75).isEqualTo(Monster.RatingRange.RANGE_3);
    }

    @Test
    void rating_이_76_이상_100이하이면_RANGE_4_을_반환() {
        //when
        Monster.RatingRange ratingRange76 = Monster.RatingRange.getRatingRangeByRating(76);

        //then
        assertThat(ratingRange76).isEqualTo(Monster.RatingRange.RANGE_4);

        //when
        Monster.RatingRange ratingRange100 = Monster.RatingRange.getRatingRangeByRating(100);

        //then
        assertThat(ratingRange100).isEqualTo(Monster.RatingRange.RANGE_4);
    }

    @Test
    void rating_이_100_초과면_예외처리() {
        //then
        assertThatThrownBy(() -> Monster.RatingRange.getRatingRangeByRating(101))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
