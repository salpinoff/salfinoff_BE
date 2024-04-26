package com.server.salpinoffServer.member.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

public class MemberSteps {

    public static final String PATH_PREFIX = "api/v1/members";

    public static ExtractableResponse<Response> 로그인_카카오(String code) {
        return 로그인_카카오(RestAssured.given().log().all(), code);
    }

    public static ExtractableResponse<Response> 로그인_카카오(RequestSpecification requestSpecification, String code) {
        Map<String, String> params = new HashMap<>();
        params.put("code", code);

        return requestSpecification
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().post(PATH_PREFIX + "/login/kakao")
                .then().statusCode(HttpStatus.OK.value())
                .log().all().extract();
    }

}
