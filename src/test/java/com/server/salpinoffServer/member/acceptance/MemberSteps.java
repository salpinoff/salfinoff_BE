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

    public static ExtractableResponse<Response> 토큰_재발급(String refreshToken) {
        return 토큰_재발급(RestAssured.given().log().all(), refreshToken);
    }

    public static ExtractableResponse<Response> 토큰_재발급(RequestSpecification requestSpecification, String refreshToken) {
        Map<String, String> params = new HashMap<>();
        params.put("refreshToken", refreshToken);

        return requestSpecification
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().post(PATH_PREFIX + "/token/refresh")
                .then().statusCode(HttpStatus.OK.value())
                .log().all().extract();
    }

    public static ExtractableResponse<Response> 로그아웃() {
        return 로그아웃(RestAssured.given().log().all());
    }

    public static ExtractableResponse<Response> 로그아웃(RequestSpecification requestSpecification) {

        return requestSpecification
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post(PATH_PREFIX + "/logout")
                .then().statusCode(HttpStatus.OK.value())
                .log().all().extract();
    }

    public static Map<String, Object> 회원정보_등록_요청값(String username) {
        Map<String, Object> req = new HashMap<>();

        req.put("username", username);

        return req;
    }

    public static ExtractableResponse<Response> 회원정보_등록(String accessToken, Map<String, Object> variables) {
        return 회원정보_등록(RestAssured.given().auth().oauth2(accessToken).log().all(), variables);
    }

    public static ExtractableResponse<Response> 회원정보_등록(RequestSpecification requestSpecification,
                                                        Map<String, Object> variables) {

        return requestSpecification
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(variables)
                .when().post(PATH_PREFIX + "/my")
                .then().statusCode(HttpStatus.OK.value())
                .log().all().extract();
    }

    public static Map<String, Object> 회원정보_수정_요청값(String username) {
        Map<String, Object> req = new HashMap<>();

        req.put("username", username);

        return req;
    }

    public static ExtractableResponse<Response> 회원정보_수정(String accessToken, Map<String, Object> variables) {
        return 회원정보_수정(RestAssured.given().auth().oauth2(accessToken).log().all(), variables);
    }

    public static ExtractableResponse<Response> 회원정보_수정(RequestSpecification requestSpecification,
                                                        Map<String, Object> variables) {

        return requestSpecification
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(variables)
                .when().put(PATH_PREFIX + "/my")
                .then().statusCode(HttpStatus.OK.value())
                .log().all().extract();
    }
}
