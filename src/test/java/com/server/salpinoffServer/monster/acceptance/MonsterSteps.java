package com.server.salpinoffServer.monster.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

public class MonsterSteps {

    public static final String PATH_PREFIX = "api/v1/monsters";
/*

    public static ExtractableResponse<Response> 몬스터_삭제(Long monsterId) {
        return 몬스터_삭제(RestAssured.given().log().all(), monsterId);
    }

    public static ExtractableResponse<Response> 몬스터_삭제(RequestSpecification requestSpecification, Long monsterId) {

        return requestSpecification
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("monsterId", monsterId)
                .when().delete(PATH_PREFIX + "/{monsterId}")
                .then().statusCode(HttpStatus.OK.value())
                .log().all().extract();
    }
*/

    public static ExtractableResponse<Response> 몬스터_인터렉션(Long monsterId, int interactionCount) {
        return 몬스터_인터렉션(RestAssured.given().log().all(), monsterId, interactionCount);
    }

    public static ExtractableResponse<Response> 몬스터_인터렉션(RequestSpecification requestSpecification, Long monsterId,
                                                         int interactionCount) {

        return requestSpecification
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("monsterId", monsterId)
                .body(Map.of("interactionCount", interactionCount))
                .when().post(PATH_PREFIX + "/{monsterId}/interactions")
                .then().statusCode(HttpStatus.OK.value())
                .log().all().extract();
    }

    public static ExtractableResponse<Response> 응원_메시지_조회(Long monsterId) {
        return 응원_메시지_조회(RestAssured.given().log().all(), monsterId);
    }

    public static ExtractableResponse<Response> 응원_메시지_조회(RequestSpecification requestSpecification, Long monsterId) {

        return requestSpecification
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("monsterId", monsterId)
                .params("page", 1, "size", 10)
                .when().get(PATH_PREFIX + "/{monsterId}/messages")
                .then().statusCode(HttpStatus.OK.value())
                .log().all().extract();
    }

    public static ExtractableResponse<Response> 응원_메시지_확인(Long monsterId, Long messageId) {
        return 응원_메시지_확인(RestAssured.given().log().all(), monsterId, messageId);
    }

    public static ExtractableResponse<Response> 응원_메시지_확인(RequestSpecification requestSpecification, Long monsterId,
                                                          Long messageId) {

        return requestSpecification
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParams("monsterId", monsterId, "messageId", messageId)
                .when().post(PATH_PREFIX + "/{monsterId}/messages/{messageId}")
                .then().statusCode(HttpStatus.OK.value())
                .log().all().extract();
    }


    public static ExtractableResponse<Response> 응원의_메시지_보내기(Long monsterId, String sender, String content) {
        return 응원의_메시지_보내기(RestAssured.given().log().all(), monsterId, sender, content);
    }

    public static ExtractableResponse<Response> 응원의_메시지_보내기(RequestSpecification requestSpecification, Long monsterId,
                                                            String sender, String content) {
        Map<String, String> body = new HashMap<>();
        body.put("sender", sender);
        body.put("content", content);

        return requestSpecification
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("monsterId", monsterId)
                .body(body)
                .when().post(PATH_PREFIX + "/{monsterId}/encouragement")
                .then().statusCode(HttpStatus.OK.value())
                .log().all().extract();
    }
}
