package com.server.salpinoffServer.monster.acceptance;

import com.server.salpinoffServer.utils.acceptance.BaseAcceptanceTest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static com.server.salpinoffServer.monster.acceptance.MonsterSteps.*;
import static org.assertj.core.api.Assertions.assertThat;

public class MonsterAcceptanceTest extends BaseAcceptanceTest {

    /**
     * Given: rating=20 인 몬스터를 만든다.
     * When: 몬스터를 50번 interaction 한다.
     * Then: 몬스터를 조회하면 currentInteractionCount 가 50 이다.
     */
    @Test
    void interactionTest() {
        //given
        String accessToken = login();

        몬스터_생성(accessToken, 몬스터_생성_요청값("화남이", 20));

        //when
        ExtractableResponse<Response> 나의_몬스터_목록_조회_응답값 = 나의_몬스터_목록_조회(accessToken);

        long monsterId = 나의_몬스터_목록_조회_응답값.jsonPath().getLong("content[0].monsterId");

        몬스터_인터렉션(accessToken, monsterId, 50);

        //then
        ExtractableResponse<Response> 몬스터_상세_조회_응답값 = 몬스터_상세_조회(accessToken, monsterId);

        assertThat(몬스터_상세_조회_응답값.jsonPath().getInt("currentInteractionCount")).isEqualTo(50);
    }

    /**
     * Given: rating=20 인 몬스터를 만든다.
     * When: 몬스터에게 응원 메시지를 보낸다.
     * When: 응원 메시지를 확인한다.
     * Then: 몬스터를 조회하면 currentInteractionCount 가 10 이다.
     */
    @Test
    void encouragementTest() {
        //given
        String accessToken = login();

        몬스터_생성(accessToken, 몬스터_생성_요청값("화남이", 20));

        //when
        ExtractableResponse<Response> 나의_몬스터_목록_조회_응답값 = 나의_몬스터_목록_조회(accessToken);

        long monsterId = 나의_몬스터_목록_조회_응답값.jsonPath().getLong("content[0].monsterId");

        long findedMonsterId = 몬스터_상세_조회(monsterId).jsonPath().getLong("monsterId");

        응원의_메시지_보내기(findedMonsterId, "옥지", "빵빵아 힘내!");

        ExtractableResponse<Response> 응원_메시지_조회_응답값 = 응원_메시지_조회(accessToken, monsterId);

        long messageId = 응원_메시지_조회_응답값.jsonPath().getLong("content[0].messageId");

        응원_메시지_확인(accessToken, monsterId, messageId);

        //then
        ExtractableResponse<Response> 몬스터_상세_조회_응답값 = 몬스터_상세_조회(accessToken, monsterId);

        assertThat(몬스터_상세_조회_응답값.jsonPath().getInt("currentInteractionCount")).isEqualTo(10);
    }

    /**
     * Given: 몬스터를 만든다.
     * When: 몬스터에게 응원 메시지를 2번 보낸다.
     * When: 응원 메시지 하나를 확인한다.
     * Then: 응원 메시지 목록을 조회하면 확인된 메시지 개수는 1이고, 확인되지않은 메시지 개수도 1이다.
     */
    @Test
    void checkedMessageCountTest() {
        //given
        String accessToken = login();

        몬스터_생성(accessToken, 몬스터_생성_요청값("화남이", 100));

        //when
        ExtractableResponse<Response> 나의_몬스터_목록_조회_응답값 = 나의_몬스터_목록_조회(accessToken);

        long monsterId = 나의_몬스터_목록_조회_응답값.jsonPath().getLong("content[0].monsterId");

        long foundMonsterId = 몬스터_상세_조회(monsterId).jsonPath().getLong("monsterId");

        응원의_메시지_보내기(foundMonsterId, "옥지", "빵빵아 힘내!");

        응원의_메시지_보내기(foundMonsterId, "박태준", "빵빵아 유튜브 업로드좀 해줘..");

        ExtractableResponse<Response> 응원_메시지_조회_응답값 = 응원_메시지_조회(accessToken, monsterId);

        long messageId = 응원_메시지_조회_응답값.jsonPath().getLong("content[0].messageId");

        응원_메시지_확인(accessToken, monsterId, messageId);

        //then
        ExtractableResponse<Response> 메시지_확인_후_응원_메시지_조회_응답값 = 응원_메시지_조회(accessToken, monsterId);

        assertThat(메시지_확인_후_응원_메시지_조회_응답값.jsonPath().getInt("checkedMessageCount"))
                .isEqualTo(1);


        assertThat(메시지_확인_후_응원_메시지_조회_응답값.jsonPath().getInt("uncheckedMessageCount"))
                .isEqualTo(1);
    }
}
