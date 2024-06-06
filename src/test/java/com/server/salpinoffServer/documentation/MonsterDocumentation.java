package com.server.salpinoffServer.documentation;

import com.server.salpinoffServer.monster.domain.Monster;
import com.server.salpinoffServer.monster.domain.MonsterDecoration;
import com.server.salpinoffServer.monster.service.MonsterService;
import com.server.salpinoffServer.monster.service.dto.MonsterDecorationResponse;
import com.server.salpinoffServer.monster.service.dto.MonsterDetailsResponse;
import com.server.salpinoffServer.monster.service.dto.MonsterInteractionResponse;
import com.server.salpinoffServer.monster.service.dto.MonsterMessagesResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;

import static com.server.salpinoffServer.monster.acceptance.MonsterSteps.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class MonsterDocumentation extends Documentation {

    @MockBean
    private MonsterService monsterService;
/*

    @Test
    void deleteMonster() {
        //given
        setAccessToken();

        //then
        몬스터_삭제(getRequestSpecification("monster-delete").auth().oauth2("accessToken"), 1L);
    }
*/

    @Test
    void createMonster() {
        //given
        setAccessToken();

        //when
        Map<String, Object> variables = 몬스터_생성_요청값("빡침몬", 100);

        MonsterDecorationResponse monsterDecorationResponse = new MonsterDecorationResponse(1L,
                MonsterDecoration.Type.BACKGROUND_COLOR, "BLUE");

        MonsterDetailsResponse monsterDetailsResponse =
                new MonsterDetailsResponse(1L, "빡침몬", "khyou", 100,
                        60, Monster.Emotion.DEPRESSION, "거 참 퇴사하기 딱 좋은 날씨네",
                        List.of(monsterDecorationResponse));

        when(monsterService.createMonster(any(), any())).thenReturn(monsterDetailsResponse);

        //then
        몬스터_생성(getRequestSpecification("monster-creation").auth().oauth2("accessToken"),
                variables);
    }

    @Test
    void getMonster() {
        //given
        MonsterDecorationResponse monsterDecorationResponse = new MonsterDecorationResponse(1L,
                MonsterDecoration.Type.BACKGROUND_COLOR, "BLUE");

        MonsterDetailsResponse monsterDetailsResponse =
                new MonsterDetailsResponse(1L, "빡침몬", "khyou", 100,
                        60, Monster.Emotion.DEPRESSION, "거 참 퇴사하기 딱 좋은 날씨네",
                        List.of(monsterDecorationResponse));

        //when
        when(monsterService.getMonster(any(), anyLong())).thenReturn(monsterDetailsResponse);

        //then
        몬스터_상세_조회(getRequestSpecification("monster-details-read"), 1L);
    }

    @Test
    void getMyMonsters() {
        //given
        setAccessToken();

        MonsterDecorationResponse monsterDecorationResponse = new MonsterDecorationResponse(1L,
                MonsterDecoration.Type.BACKGROUND_COLOR, "BLUE");

        List<MonsterDetailsResponse> monsterDetailsResponses = List.of(
                new MonsterDetailsResponse(1L, "빡침몬", "khyou", 100,
                        60, Monster.Emotion.DEPRESSION, "거 참 퇴사하기 딱 좋은 날씨네",
                        List.of(monsterDecorationResponse)),
                new MonsterDetailsResponse(2L, "화남몬", "khyou",150,
                        10, Monster.Emotion.DEPRESSION, "화난건 뻥이고 다들 홧팅해라~!",
                        List.of(monsterDecorationResponse))
        );

        PageImpl<MonsterDetailsResponse> response =
                new PageImpl<>(monsterDetailsResponses, PageRequest.of(0, 10), 2L);

        //when
        when(monsterService.getMonstersByMember(any(), any())).thenReturn(response);

        //then
        나의_몬스터_목록_조회(getRequestSpecification("monster-my-read").auth().oauth2("accessToken"));
    }

    @Test
    void getRepMonsterByMember() {
        //given
        setAccessToken();

        MonsterDecorationResponse monsterDecorationResponse = new MonsterDecorationResponse(1L,
                MonsterDecoration.Type.BACKGROUND_COLOR, "BLUE");

        MonsterDetailsResponse monsterDetailsResponse =
                new MonsterDetailsResponse(1L, "빡침몬", "khyou", 100,
                        60, Monster.Emotion.DEPRESSION, "거 참 퇴사하기 딱 좋은 날씨네",
                        List.of(monsterDecorationResponse));

        //when
        when(monsterService.getRepMonsterByMember(any())).thenReturn(monsterDetailsResponse);

        //then
        나의_대표_퇴사몬_조회(getRequestSpecification("monster-my-rep-read").auth().oauth2("accessToken"));
    }

    @Test
    void updateMonster() {
        //given
        setAccessToken();

        //when
        Map<String, Object> variables = 몬스터_수정_요청값("화나서 치킨 시킴 ㅋㅋ");

        //then
        몬스터_수정(getRequestSpecification("monster-modification").auth().oauth2("accessToken"),
                1L, variables);
    }

    @Test
    void interactMonster() {
        //given
        setAccessToken();

        //when
        when(monsterService.interactMonster(anyLong(), anyLong(), any()))
                .thenReturn(new MonsterInteractionResponse(1L, 15));

        //then
        몬스터_인터렉션(getRequestSpecification("monster-interaction").auth().oauth2("accessToken"),
                1L, 10);
    }

    @Test
    void getMonsterMessages() {
        //given
        setAccessToken();

        //when
        List<MonsterMessagesResponse> messages = List.of(
                new MonsterMessagesResponse(1L, "옥지", "빵빵아 힘내", true),
                new MonsterMessagesResponse(3L, "박태준", "빵빵이 홧팅", false));

        when(monsterService.getMonsterMessages(anyLong(), anyLong(), any()))
                .thenReturn(new PageImpl<>(messages, PageRequest.of(0, 10), 2L));

        //then
        응원_메시지_조회(getRequestSpecification("monster-messages-read").auth().oauth2("accessToken"), 1L);
    }

    @Test
    void checkMonsterMessage() {
        //given
        setAccessToken();

        //then
        응원_메시지_확인(getRequestSpecification("monster-messages-check").auth().oauth2("accessToken"),
                1L, 1L);
    }

    @Test
    void encourageUser() {
        //then
        응원의_메시지_보내기(getRequestSpecification("encouragement-message-send"), 1L,
                "옥지", "빵빵아 힘내!");
    }
}
