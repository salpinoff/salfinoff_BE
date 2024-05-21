package com.server.salpinoffServer.member.acceptance;

import com.server.salpinoffServer.monster.acceptance.MonsterSteps;
import com.server.salpinoffServer.utils.acceptance.BaseAcceptanceTest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.server.salpinoffServer.member.acceptance.MemberSteps.로그인_카카오;
import static com.server.salpinoffServer.member.acceptance.MemberSteps.회원정보_등록;
import static org.assertj.core.api.Assertions.assertThat;

public class MemberAcceptanceTest extends BaseAcceptanceTest {

    /**
     * When: 유저가 첫 로그인을 한다.
     * Then: 유저의 로그인 코드가 100 이다.
     * Given: 회원 정보를 등록한다.
     * When: 유저가 로그인을 한다.
     * Then: 유저의 로그인 코드가 101 이다.
     * Given: 유저가 몬스터를 만든다.
     * When: 유저가 로그인을 한다.
     * Then: 유저의 로그인 코드가 102 이다.
     */
    @Test
    void loginTest() {
        //when
        ExtractableResponse<Response> 로그인_응답값_100 = 로그인_카카오("1");

        //then
        assertThat(로그인_응답값_100.jsonPath().getString("code")).isEqualTo("100");

        //given
        String accessToken = 로그인_응답값_100.jsonPath().getString("accessToken");

        회원정보_등록(accessToken, Map.of("username", "빵빵이"));

        //when
        ExtractableResponse<Response> 로그인_응답값_200 = 로그인_카카오("1");

        //then
        assertThat(로그인_응답값_200.jsonPath().getString("code")).isEqualTo("101");

        //given
        MonsterSteps.몬스터_생성(accessToken, MonsterSteps.몬스터_생성_요청값("빡침이"));

        //when
        ExtractableResponse<Response> 로그인_응답값_300 = 로그인_카카오("1");

        //then
        assertThat(로그인_응답값_300.jsonPath().getString("code")).isEqualTo("102");
    }
}
