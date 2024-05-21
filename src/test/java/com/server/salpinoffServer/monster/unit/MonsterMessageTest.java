package com.server.salpinoffServer.monster.unit;

import com.server.salpinoffServer.monster.domain.MonsterMessage;
import org.junit.jupiter.api.Test;
import org.springframework.security.access.AccessDeniedException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MonsterMessageTest {

    @Test
    void 메시지_수신자가_아닌_몬스터가_확인하면_예외처리() {
        //given
        MonsterMessage monsterMessage = new MonsterMessage(1L, "옥지", "빵빵아 힘내!");

        //then
        assertThatThrownBy(() -> monsterMessage.check(2L))
                .isInstanceOf(AccessDeniedException.class);
    }
}
