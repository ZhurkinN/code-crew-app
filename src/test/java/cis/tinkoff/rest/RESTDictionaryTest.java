package cis.tinkoff.rest;

import cis.tinkoff.controller.model.custom.InteractResumeDTO;
import cis.tinkoff.model.DirectionDictionary;
import cis.tinkoff.model.ProjectStatusDictionary;
import cis.tinkoff.model.enumerated.Direction;
import cis.tinkoff.model.enumerated.ProjectStatus;
import cis.tinkoff.rest.model.UserLoginDTO;
import cis.tinkoff.spec.Specifications;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static io.restassured.RestAssured.given;

@MicronautTest
@Testcontainers
public class RESTDictionaryTest extends AbstractIntegrationTest {

    private static String TOKEN = "";

    @BeforeAll
    static void init() {
        RestAssured.defaultParser = Parser.JSON;
        UserLoginDTO dto = new UserLoginDTO("alex@mail.ru", "123");

        Specifications.installSpecification(Specifications.requestSpec("/auth/login"), Specifications.responseSpec(200));

        TOKEN = given()
                .urlEncodingEnabled(false)
                .body(dto)
                .when()
                .post("/auth/login")
                .then()
                .extract()
                .path("access_token");
    }

    @Test
    public void testGetDirectionsEndpoint() {
        Specifications.installSpecification(Specifications.requestSpec("/api/v1/dictionaries/directions"), Specifications.responseSpec(200));

        List<DirectionDictionary> directions
                = given()
                .when()
                .get("/api/v1/dictionaries/directions")
                .then()
                .extract()
                .body()
                .jsonPath().getList(".", DirectionDictionary.class);

        int expectedSize = 8;

        DirectionDictionary[] expectedDirections = new DirectionDictionary[8];

        expectedDirections[0] = new DirectionDictionary(Direction.BACKEND, "Backend-developer");
        expectedDirections[1] = new DirectionDictionary(Direction.FRONTEND, "Frontend-developer");
        expectedDirections[2] = new DirectionDictionary(Direction.DATA_SCIENCE, "Data Science Developer");
        expectedDirections[3] = new DirectionDictionary(Direction.QA, "QA-engineer");
        expectedDirections[4] = new DirectionDictionary(Direction.ANALYST, "Analyst");
        expectedDirections[5] = new DirectionDictionary(Direction.ML, "Machine Learning Engineer");
        expectedDirections[6] = new DirectionDictionary(Direction.DEVOPS, "DevOps Engineer");
        expectedDirections[7] = new DirectionDictionary(Direction.FULLSTACK, "Fullstack-developer");

        Assertions.assertEquals(expectedSize, directions.size());

        for (int i = 0; i < 8; i++) {
            Assertions.assertEquals(expectedDirections[i], directions.get(i));
        }
    }

    @Test
    public void testGetProjectStatusesEndpoint() {
        Specifications.installSpecification(Specifications.requestSpec("/api/v1/dictionaries/project-statuses"), Specifications.responseSpec(200));

        List<ProjectStatusDictionary> projectStatuses
                = given()
                .when()
                .get("/api/v1/dictionaries/project-statuses")
                .then()
                .extract()
                .body()
                .jsonPath().getList(".", ProjectStatusDictionary.class);

        int expectedSize = 4;

        ProjectStatusDictionary[] expectedProjectStatuses = new ProjectStatusDictionary[expectedSize];

        expectedProjectStatuses[0] = new ProjectStatusDictionary(ProjectStatus.PREPARING, "Project is in preparation stage");
        expectedProjectStatuses[1] = new ProjectStatusDictionary(ProjectStatus.IN_PROGRESS, "Project is in progress");
        expectedProjectStatuses[2] = new ProjectStatusDictionary(ProjectStatus.CLOSED, "Project is closed");
        expectedProjectStatuses[3] = new ProjectStatusDictionary(ProjectStatus.FROZEN, "Project is frozen");

        Assertions.assertEquals(expectedSize, projectStatuses.size());

        for (int i = 0; i < expectedSize; i++) {
            Assertions.assertEquals(expectedProjectStatuses[i], projectStatuses.get(i));
        }
    }

    @Test
    public void testGetUsersResumeAvailableDirectionsEndpoint() {
        Specifications.installSpecification(Specifications.requestSpec("/api/v1/dictionaries/directions/resumes"), Specifications.responseSpec(200));

        List<DirectionDictionary> directions = given()
                .when()
                .header("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", ContentType.JSON)
                .get("/api/v1/dictionaries/directions/resumes")
                .then()
                .extract()
                .body().jsonPath().getList(".", DirectionDictionary.class);

        int expectedSize = 7;

        Assertions.assertEquals(expectedSize, directions.size());

        Specifications.installSpecification(Specifications.requestSpec("/api/v1/resumes"), Specifications.responseSpec(200));

        InteractResumeDTO resumeDTO
                = new InteractResumeDTO("New resume", List.of("JavaScript", "React"), "FRONTEND");

        given()
                .urlEncodingEnabled(false)
                .body(resumeDTO)
                .when()
                .header("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", ContentType.JSON)
                .post("/api/v1/resumes")
                .then()
                .log().all();

        Specifications.installSpecification(Specifications.requestSpec("/api/v1/directions/resumes"), Specifications.responseSpec(200));

        directions = given()
                .when()
                .header("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", ContentType.JSON)
                .get("/api/v1/dictionaries/directions/resumes")
                .then()
                .extract()
                .body().jsonPath().getList(".", DirectionDictionary.class);

        Assertions.assertEquals(expectedSize - 1, directions.size());
    }
}
