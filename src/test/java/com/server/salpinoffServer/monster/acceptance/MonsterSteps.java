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

    public static ExtractableResponse<Response> 몬스터_상세_조회(Long monsterId) {
        return 몬스터_상세_조회(RestAssured.given().log().all(), monsterId);
    }

    public static ExtractableResponse<Response> 몬스터_상세_조회(RequestSpecification requestSpecification, Long monsterId) {

        return requestSpecification
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("monsterId", monsterId)
                .when().get(PATH_PREFIX + "/{monsterId}")
                .then().statusCode(HttpStatus.OK.value())
                .log().all().extract();
    }

    public static ExtractableResponse<Response> 나의_몬스터_목록_조회(String accessToken) {
        return 나의_몬스터_목록_조회(RestAssured.given().auth().oauth2(accessToken).log().all());
    }

    public static ExtractableResponse<Response> 나의_몬스터_목록_조회(RequestSpecification requestSpecification) {

        return requestSpecification
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(PATH_PREFIX + "/my")
                .then().statusCode(HttpStatus.OK.value())
                .log().all().extract();
    }

    public static Map<String, Object> 몬스터_생성_요청값(String monsterName) {
        Map<String, Object> req = new HashMap<>();

        req.put("monsterName", monsterName);
        req.put("interactionCount", "100");
        req.put("emotion", "DEPRESSION");
        req.put("content", "거 참 퇴사하기 딱 좋은 날씨네");
        req.put("monsterDecoration", Map.of("backgroundColor", "BLUE"));

        return req;
    }

    public static ExtractableResponse<Response> 몬스터_생성(String accessToken, Map<String, Object> variables) {
        return 몬스터_생성(RestAssured.given().auth().oauth2(accessToken).log().all(), variables);
    }

    public static ExtractableResponse<Response> 몬스터_생성(RequestSpecification requestSpecification,
                                                       Map<String, Object> variables) {

        return requestSpecification
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(variables)
                .when().post(PATH_PREFIX)
                .then().statusCode(HttpStatus.OK.value())
                .log().all().extract();
    }

    public static Map<String, Object> 몬스터_수정_요청값(String content) {
        Map<String, Object> req = new HashMap<>();

        req.put("content", content);

        return req;
    }

    public static ExtractableResponse<Response> 몬스터_수정(String accessToken, Long monsterId,
                                                       Map<String, Object> variables) {
        return 몬스터_수정(RestAssured.given().auth().oauth2(accessToken).log().all(), monsterId, variables);
    }

    public static ExtractableResponse<Response> 몬스터_수정(RequestSpecification requestSpecification,
                                                       Long monsterId, Map<String, Object> variables) {

        return requestSpecification
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(variables)
                .pathParam("monsterId", monsterId)
                .when().put(PATH_PREFIX + "/{monsterId}")
                .then().statusCode(HttpStatus.OK.value())
                .log().all().extract();
    }

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

    public static ExtractableResponse<Response> 몬스터_인터렉션(String accessToken, Long monsterId, int interactionCount) {
        return 몬스터_인터렉션(RestAssured.given().auth().oauth2(accessToken).log().all(), monsterId, interactionCount);
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

    public static ExtractableResponse<Response> 응원_메시지_조회(String accessToken, Long monsterId) {
        return 응원_메시지_조회(RestAssured.given().auth().oauth2(accessToken).log().all(), monsterId);
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

    public static ExtractableResponse<Response> 응원_메시지_확인(String accessToken, Long monsterId, Long messageId) {
        return 응원_메시지_확인(RestAssured.given().auth().oauth2(accessToken).log().all(), monsterId, messageId);
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
