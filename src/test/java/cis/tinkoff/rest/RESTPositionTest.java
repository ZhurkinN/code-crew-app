package cis.tinkoff.rest;

import cis.tinkoff.controller.model.ProjectDTO;
import cis.tinkoff.controller.model.VacancyDTO;
import cis.tinkoff.model.DirectionDictionary;
import cis.tinkoff.model.enumerated.Direction;
import cis.tinkoff.rest.model.UserLoginDTO;
import cis.tinkoff.spec.Specifications;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;

import static cis.tinkoff.support.exceptions.constants.ErrorDisplayMessageKeeper.*;
import static io.restassured.RestAssured.given;

@MicronautTest
@Testcontainers
public class RESTPositionTest {

    @Container
    public static TestSQLContainer container = TestSQLContainer.getInstance();

    private static String TOKEN_1 = "";
    private static String TOKEN_2 = "";
    private static String USER_MAIL = "alex@mail.ru";
    private static String USER_PASSWORD = "123";

    @BeforeAll
    static void setUp() {
        RestAssured.defaultParser = Parser.JSON;
        UserLoginDTO dto = new UserLoginDTO(USER_MAIL, USER_PASSWORD);

        Specifications.installSpecification(Specifications.requestSpec("/auth/login"), Specifications.responseSpec(200));

        TOKEN_1 = given()
                .urlEncodingEnabled(false)
                .body(dto)
                .when()
                .post("/auth/login")
                .then()
                .extract()
                .path("access_token");

        UserLoginDTO dto2 = new UserLoginDTO("weiber@mail.ru", "123");

        Specifications.installSpecification(Specifications.requestSpec("/auth/login"), Specifications.responseSpec(200));

        TOKEN_2 = given()
                .urlEncodingEnabled(false)
                .body(dto2)
                .when()
                .post("/auth/login")
                .then()
                .extract()
                .path("access_token");
    }

    @Test
    public void testGetVacancyById() {
        Specifications.installSpecification(Specifications.requestSpec("/api/v1/positions/1"), Specifications.responseSpec(200));

        Long expectedId = 1L;
        DirectionDictionary expectedDirection
                = new DirectionDictionary(Direction.BACKEND, "Backend-developer");
        List<String> expectedSkills = List.of("java", "postgres", "spring", "maven");
        ProjectDTO expectedProject = ProjectDTO.builder().id(1L).build();

        VacancyDTO dto = given()
                .when()
                .header("Authorization", "Bearer " + TOKEN_1)
                .header("Content-Type", ContentType.JSON)
                .get("/api/v1/positions/1")
                .then()
                .extract()
                .body().as(VacancyDTO.class);

        Assertions.assertNotNull(dto);
        Assertions.assertNotNull(dto.getCreatedWhen());
        Assertions.assertNotNull(dto.getDescription());
        Assertions.assertEquals(expectedId, dto.getId());
        Assertions.assertEquals(expectedDirection, dto.getDirection());
        Assertions.assertEquals(expectedSkills, dto.getSkills());
        Assertions.assertEquals(expectedProject.getId(), dto.getProject().getId());
    }

    @Test
    public void shouldReturnExceptionWhenFindingFilledVacancy() {
        int id = 2;
        Specifications.installSpecification(Specifications.requestSpec("/api/v1/positions/" + id), Specifications.responseSpec(404));

        Response response = given()
                .when()
                .header("Authorization", "Bearer " + TOKEN_1)
                .header("Content-Type", ContentType.JSON)
                .get("/api/v1/positions/" + id)
                .then()
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();
        String errorMessage = jsonPath.get("message");

        Assertions.assertEquals(String.format(POSITION_NOT_FOUND, id), errorMessage);
    }

    @Test
    public void testGetProjectVacancies() {
        int id = 1;
        Specifications.installSpecification(Specifications.requestSpec("/api/v1/positions/projects?projectId=" + id + "&isVisible=true"), Specifications.responseSpec(200));

        List<VacancyDTO> expectedVacancies = new ArrayList<>();

        VacancyDTO expectedVacancy1 = VacancyDTO.builder()
                .id(1L)
                .isVisible(true)
                .direction(new DirectionDictionary(Direction.BACKEND, "Backend-developer"))
                .skills(List.of("java", "postgres", "spring", "maven"))
                .build();

        VacancyDTO expectedVacancy2 = VacancyDTO.builder()
                .id(3L)
                .isVisible(true)
                .direction(new DirectionDictionary(Direction.QA, "QA-engineer"))
                .skills(List.of("java", "mockito", "junit"))
                .build();

        expectedVacancies.add(expectedVacancy1);
        expectedVacancies.add(expectedVacancy2);

        List<VacancyDTO> dtos = given()
                .when()
                .header("Authorization", "Bearer " + TOKEN_1)
                .header("Content-Type", ContentType.JSON)
                .get("/api/v1/positions/projects?projectId=" + id + "&isVisible=true")
                .then()
                .extract()
                .body().jsonPath().getList(".", VacancyDTO.class);

        Assertions.assertEquals(expectedVacancies.size(), dtos.size());

        for (int i = 0; i < expectedVacancies.size(); i++) {
            VacancyDTO expectedVacancy = expectedVacancies.get(i);
            VacancyDTO vacancy = dtos.get(i);
            Assertions.assertEquals(expectedVacancy.getId(), vacancy.getId());
            Assertions.assertEquals(expectedVacancy.getDirection(), vacancy.getDirection());
            Assertions.assertEquals(expectedVacancy.getIsVisible(), vacancy.getIsVisible());
            Assertions.assertEquals(expectedVacancy.getSkills(), vacancy.getSkills());
        }
    }

    @Test
    public void testChangeVisibility() {
        int id = 4;
        Specifications.installSpecification(Specifications.requestSpec("/api/v1/positions/visible/" + id), Specifications.responseSpec(200));

        boolean expectedIsVisible = false;
        VacancyDTO dto = given()
                .when()
                .header("Authorization", "Bearer " + TOKEN_1)
                .header("Content-Type", ContentType.JSON)
                .post("/api/v1/positions/visible/" + id)
                .then()
                .extract()
                .body().as(VacancyDTO.class);

        Assertions.assertEquals(expectedIsVisible, dto.getIsVisible());

        Specifications.installSpecification(Specifications.requestSpec("/api/v1/positions/visible/" + id), Specifications.responseSpec(200));

        dto = given()
                .when()
                .header("Authorization", "Bearer " + TOKEN_1)
                .header("Content-Type", ContentType.JSON)
                .post("/api/v1/positions/visible/" + id)
                .then()
                .extract()
                .body().as(VacancyDTO.class);

        Assertions.assertEquals(true, dto.getIsVisible());
    }

    @Test
    public void shouldReturnExceptionWhenChangingVisibilityByNotLead() {
        int id = 1;
        Specifications.installSpecification(Specifications.requestSpec("/api/v1/positions/visible/" + id), Specifications.responseSpec(406));

        Response response = given()
                .when()
                .header("Authorization", "Bearer " + TOKEN_1)
                .header("Content-Type", ContentType.JSON)
                .post("/api/v1/positions/visible/" + id)
                .then()
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();
        String errorMessage = jsonPath.get("message");

        Assertions.assertEquals(String.format(INACCESSIBLE_PROJECT_ACTION, USER_MAIL, 1), errorMessage);
    }

    @Test
    public void shouldReturnExceptionWhenDeletingByNotLead() {
        int id = 1;
        Specifications.installSpecification(Specifications.requestSpec("/api/v1/positions/" + id), Specifications.responseSpec(406));

        Response response = given()
                .when()
                .header("Authorization", "Bearer " + TOKEN_1)
                .header("Content-Type", ContentType.JSON)
                .delete("/api/v1/positions/" + id)
                .then()
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();
        String errorMessage = jsonPath.get("message");

        Assertions.assertEquals(String.format(INACCESSIBLE_PROJECT_ACTION, USER_MAIL, 1), errorMessage);
    }

    @Test
    @AfterAll
    public static void testSoftDelete() {
        int id = 1;
        Specifications.installSpecification(Specifications.requestSpec("/api/v1/positions/projects?projectId=" + id + "&isVisible=true"), Specifications.responseSpec(200));

        List<VacancyDTO> dtos = given()
                .when()
                .header("Authorization", "Bearer " + TOKEN_2)
                .header("Content-Type", ContentType.JSON)
                .get("/api/v1/positions/projects?projectId=" + id + "&isVisible=true")
                .then()
                .extract()
                .body().jsonPath().getList(".", VacancyDTO.class);

        int expectedVacancySize = dtos.size() - 1;

        Specifications.installSpecification(Specifications.requestSpec("/api/v1/positions/" + 1), Specifications.responseSpec(200));
        Response response = given()
                .when()
                .header("Authorization", "Bearer " + TOKEN_2)
                .header("Content-Type", ContentType.JSON)
                .delete("/api/v1/positions/" + 1)
                .then()
                .extract()
                .response();

        Specifications.installSpecification(Specifications.requestSpec("/api/v1/positions/projects?projectId=" + id + "&isVisible=true"), Specifications.responseSpec(200));

        dtos = given()
                .when()
                .header("Authorization", "Bearer " + TOKEN_2)
                .header("Content-Type", ContentType.JSON)
                .get("/api/v1/positions/projects?projectId=" + id + "&isVisible=true")
                .then()
                .extract()
                .body().jsonPath().getList(".", VacancyDTO.class);

        Assertions.assertEquals(expectedVacancySize, dtos.size());
    }
}
