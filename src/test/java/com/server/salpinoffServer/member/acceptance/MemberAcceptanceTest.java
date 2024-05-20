package com.server.salpinoffServer.member.acceptance;

import com.server.salpinoffServer.member.service.OAuthManager;
import com.server.salpinoffServer.utils.acceptance.BaseAcceptanceTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

public class MemberAcceptanceTest extends BaseAcceptanceTest {

    @MockBean
    private OAuthManager oAuthManager;

    @Test
    void 인수_테스트() {
        //given

        //when

        //then
    }
}
