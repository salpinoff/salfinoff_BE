package com.server.salpinoffServer.documentation;

import com.server.salpinoffServer.infra.auth.jwt.JwtManager;
import com.server.salpinoffServer.member.domain.Member;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.restassured.RestAssuredRestDocumentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(RestDocumentationExtension.class)
public class Documentation {

    @LocalServerPort
    int port;

    @MockBean
    private JwtManager jwtManager;

    RequestSpecification spec;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        RestAssured.port = port;
        this.spec = new RequestSpecBuilder()
                .addFilter(RestAssuredRestDocumentation.documentationConfiguration(restDocumentation))
                .build();
    }

    RequestSpecification getRequestSpecification(String identifier) {
        return RestAssured
                .given(spec).log().all()
                .filter(RestAssuredRestDocumentation.document(identifier,
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    void setAccessToken() {
        //security filter 에서 걸리지 않기 위함
        when(jwtManager.getMemberId(any())).thenReturn(1L);
        when(jwtManager.getAuthority(any())).thenReturn(Member.Authority.USER.name());
    }
}
