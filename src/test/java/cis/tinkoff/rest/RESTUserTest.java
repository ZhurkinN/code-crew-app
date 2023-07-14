package cis.tinkoff.rest;

import cis.tinkoff.model.DirectionDictionary;
import cis.tinkoff.rest.model.UserLoginDTO;
import cis.tinkoff.spec.Specifications;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;

import cis.tinkoff.spec.Specifications;

import java.util.List;

@MicronautTest
@Testcontainers
public class RESTUserTest extends AbstractIntegrationTest {

    private static final String BASE_USERS_URL = "localhost:8080/api/v1/users/";

    @BeforeAll
    static void init() {
        RestAssured.defaultParser = Parser.JSON;
    }

    @Test
    public void testFindAllEndpoint() {
        UserLoginDTO dto = new UserLoginDTO("alex@mail.ru", "123");

        Specifications.installSpecification(Specifications.requestSpec("/auth/login"), Specifications.responseSpec(200));

        String token = given()
                .urlEncodingEnabled(false)
                .body(dto)
                .when()
                .post("/auth/login")
                .then()
                .extract()
                .path("access_token");

        given()
                .when()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", ContentType.JSON)
                .get("/api/v1/users/all")
                .then()
                .statusCode(200);
    }
}
