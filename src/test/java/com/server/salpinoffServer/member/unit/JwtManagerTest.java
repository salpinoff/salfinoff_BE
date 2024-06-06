package com.server.salpinoffServer.member.unit;

import com.server.salpinoffServer.infra.auth.jwt.JwtManager;
import com.server.salpinoffServer.member.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JwtManagerTest {

    @Mock
    private Member member;

    @BeforeEach
    void setUp() {
        when(member.getId()).thenReturn(1L);
        when(member.getAuthority()).thenReturn(Member.Authority.USER);
        when(member.getUsername()).thenReturn("khyou");
    }

    @Test
    void 만료된_토큰인지_확인() {
        //given
        JwtManager jwtManager = new JwtManager(0, 0,
                "124adfagasdfaadsfasdfadsfadfadf123");

        //when
        String accessToken = jwtManager.createAccessToken(member);

        //then
        assertThat(jwtManager.isExpired(accessToken)).isTrue();

        //when
        String refreshToken = jwtManager.createRefreshToken(member);

        //then
        assertThat(jwtManager.isExpired(refreshToken)).isTrue();
    }

    @Test
    void 만료되지_않은_토큰인지_확인() {
        //given
        JwtManager jwtManager = new JwtManager(10000, 10000,
                "124adfagasdfaadsfasdfadsfadfadf123");

        //when
        String accessToken = jwtManager.createAccessToken(member);

        //then
        assertThat(jwtManager.isExpired(accessToken)).isFalse();

        //when
        String refreshToken = jwtManager.createRefreshToken(member);

        //then
        assertThat(jwtManager.isExpired(refreshToken)).isFalse();
    }
}
