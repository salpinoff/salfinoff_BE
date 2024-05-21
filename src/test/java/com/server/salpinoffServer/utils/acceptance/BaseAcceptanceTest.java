package com.server.salpinoffServer.utils.acceptance;

import com.server.salpinoffServer.member.service.OAuthManager;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static com.server.salpinoffServer.member.acceptance.MemberSteps.로그인_카카오;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class BaseAcceptanceTest {

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @MockBean
    protected OAuthManager oAuthManager;

    protected String login() {
        ExtractableResponse<Response> 로그인_응답값 = 로그인_카카오("1");

        return 로그인_응답값.jsonPath().getString("accessToken");
    }

    @BeforeEach
    public void setUp() {
        RestAssured.port = RestAssured.DEFAULT_PORT;
        databaseCleanup.execute();
    }
}
