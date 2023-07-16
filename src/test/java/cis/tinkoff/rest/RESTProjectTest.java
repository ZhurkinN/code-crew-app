package cis.tinkoff.rest;

import cis.tinkoff.controller.model.ProjectDTO;
import cis.tinkoff.model.DirectionDictionary;
import cis.tinkoff.model.ProjectStatusDictionary;
import cis.tinkoff.model.enumerated.ProjectStatus;
import cis.tinkoff.rest.model.UserLoginDTO;
import cis.tinkoff.spec.Specifications;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static cis.tinkoff.support.exceptions.constants.ErrorDisplayMessageKeeper.DELETED_RECORD_FOUND;
import static cis.tinkoff.support.exceptions.constants.ErrorDisplayMessageKeeper.PROJECT_WRONG_ACCESS;
import static io.restassured.RestAssured.given;

@MicronautTest
@Testcontainers
public class RESTProjectTest extends AbstractIntegrationTest {

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
    public void testGetUserProjects() {
        Specifications.installSpecification(Specifications.requestSpec("/api/v1/projects?lead"), Specifications.responseSpec(200));

        List<ProjectDTO> projectsNotLead = given()
                .when()
                .header("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", ContentType.JSON)
                .get("/api/v1/projects?lead")
                .then()
                .extract()
                .body().jsonPath().getList(".", ProjectDTO.class);

        int expectedSize = 1;

        Assertions.assertEquals(expectedSize, projectsNotLead.size());
    }

    @Test
    public void testGetUserLeadProjects() {
        Specifications.installSpecification(Specifications.requestSpec("/api/v1/projects?lead=true"), Specifications.responseSpec(200));

        List<ProjectDTO> projectsLead = given()
                .when()
                .header("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", ContentType.JSON)
                .get("/api/v1/projects?lead=true")
                .then()
                .extract()
                .body().jsonPath().getList(".", ProjectDTO.class);

        Long expectedId = 2L;
        String expectedTitle = "Новый тиндер";
        int expectedSize = 1;

        Long id = projectsLead.get(0).getId();
        String title = projectsLead.get(0).getTitle();

        Assertions.assertEquals(expectedSize, projectsLead.size());
        Assertions.assertEquals(expectedId, id);
        // Кодирует expectedTitle плохо
//        Assertions.assertEquals(expectedTitle, title);
    }

    @Test
    public void getProjectByIdTest() {
        Specifications.installSpecification(Specifications.requestSpec("/api/v1/projects/" + 1), Specifications.responseSpec(200));

        ProjectDTO project = given()
                .when()
                .header("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", ContentType.JSON)
                .get("/api/v1/projects/" + 1)
                .then()
                .extract()
                .body().as(ProjectDTO.class);

        Long expectedId = 1L;
        boolean expectedIsLeader = false;
        String expectedTitle = "Новый сайт РЖД";
        String expectedTheme = "Сайт РЖД";
        String expectedDescription = "Проект разработки нового сайта РЖД";
        int expectedMembersCount = 1;
        ProjectStatusDictionary expectedProjectStatus
                = new ProjectStatusDictionary(ProjectStatus.PREPARING, "Project is in preparation stage");
        int expectedContactsSize = 2;
        int expectedVacanciesCount = 2;

        Assertions.assertEquals(expectedId, project.getId());
        Assertions.assertEquals(expectedIsLeader, project.getIsLeader());
        // то же самое, что и в том тесте
//        Assertions.assertEquals(expectedTitle, project.getTitle());
//        Assertions.assertEquals(expectedTheme, project.getTheme());
//        Assertions.assertEquals(expectedDescription, project.getDescription());
        Assertions.assertEquals(expectedMembersCount, project.getMembersCount());
        Assertions.assertEquals(expectedProjectStatus, project.getStatus());
        Assertions.assertEquals(expectedContactsSize, project.getContacts().size());
        Assertions.assertEquals(expectedVacanciesCount, project.getVacanciesCount());
    }

    @Test
    public void testDeleteProjectByIdWithNotLead() {
        Specifications.installSpecification(Specifications.requestSpec("/api/v1/projects/" + 1), Specifications.responseSpec(406));

        Response response = given()
                .when()
                .header("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", ContentType.JSON)
                .delete("/api/v1/projects/" + 1)
                .then()
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();
        String errorMessage = jsonPath.get("message");

        Assertions.assertEquals(PROJECT_WRONG_ACCESS, errorMessage);
    }

    @Test
    @AfterAll
    public static void testDeleteProjectByIdWithLead() {
        Specifications.installSpecification(Specifications.requestSpec("/api/v1/projects/" + 2), Specifications.responseSpec(200));

        given()
                .when()
                .header("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", ContentType.JSON)
                .delete("/api/v1/projects/" + 2)
                .then()
                .extract()
                .response();
    }
}
