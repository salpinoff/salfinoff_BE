package com.server.salpinoffServer.documentation;

import com.server.salpinoffServer.monster.service.MonsterService;
import com.server.salpinoffServer.monster.service.dto.MonsterInteractionResponse;
import com.server.salpinoffServer.monster.service.dto.MonsterMessagesResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static com.server.salpinoffServer.monster.acceptance.MonsterSteps.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class MonsterDocumentation extends Documentation {

    @MockBean
    private MonsterService monsterService;

    @Test
    void deleteMonster() {
        //given
        setAccessToken();

        //then
        몬스터_삭제(getRequestSpecification("monster-delete").auth().oauth2("accessToken"), 1L);
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
