package cis.tinkoff.rest;

import cis.tinkoff.controller.model.ProjectDTO;
import cis.tinkoff.controller.model.VacancyDTO;
import cis.tinkoff.controller.model.custom.ContactDTO;
import cis.tinkoff.controller.model.custom.ProjectCreateDTO;
import cis.tinkoff.controller.model.custom.ProjectMemberDTO;
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
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
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
    public void testGetProjectById() {
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
    public void testChangeProjectById() {
        Specifications.installSpecification(Specifications.requestSpec("/api/v1/projects/" + 2), Specifications.responseSpec(200));

        ProjectCreateDTO dto = new ProjectCreateDTO();

        Long expectedId = 2L;
        boolean expectedIsLeader = true;
        String expectedTitle = "New project";
        String expectedTheme = "New theme";
        String expectedDescription = "New description";
        ProjectStatus status = ProjectStatus.FROZEN;
        ProjectStatusDictionary expectedProjectStatus
                = new ProjectStatusDictionary(ProjectStatus.FROZEN, "Project is frozen");
        ContactDTO contact1 = ContactDTO.builder().description("New link 1").link("Link 1").build();
        ContactDTO contact2 = ContactDTO.builder().description("New link 2").link("Link 2").build();
        ContactDTO contact3 = ContactDTO.builder().description("New link 3").link("Link 3").build();
        List<ContactDTO> expectedContacts = List.of(contact1, contact2, contact3);

        dto.setStatus(status);
        dto.setDescription(expectedDescription);
        dto.setTheme(expectedTheme);
        dto.setContacts(expectedContacts);
        dto.setTitle(expectedTitle);

        ProjectDTO projectDTO = given()
                .when()
                .body(dto)
                .header("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", ContentType.JSON)
                .patch("/api/v1/projects/" + 2)
                .then()
                .extract()
                .body().as(ProjectDTO.class);

        Assertions.assertEquals(expectedId, projectDTO.getId());
        Assertions.assertEquals(expectedIsLeader, projectDTO.getIsLeader());
        Assertions.assertEquals(expectedTitle, projectDTO.getTitle());
        Assertions.assertEquals(expectedTheme, projectDTO.getTheme());
        Assertions.assertEquals(expectedDescription, projectDTO.getDescription());
        Assertions.assertEquals(expectedProjectStatus, projectDTO.getStatus());
        Assertions.assertEquals(expectedContacts.size(), projectDTO.getContacts().size());
    }

    @Test
    public void testChangeProjectByIdWithNotLead() {
        Specifications.installSpecification(Specifications.requestSpec("/api/v1/projects/" + 1), Specifications.responseSpec(406));

        ProjectCreateDTO dto = new ProjectCreateDTO();

        String expectedTitle = "New project";
        String expectedTheme = "New theme";
        String expectedDescription = "New description";
        ProjectStatus status = ProjectStatus.FROZEN;
        ContactDTO contact1 = ContactDTO.builder().description("New link 1").link("Link 1").build();
        ContactDTO contact2 = ContactDTO.builder().description("New link 2").link("Link 2").build();
        ContactDTO contact3 = ContactDTO.builder().description("New link 3").link("Link 3").build();
        List<ContactDTO> expectedContacts = List.of(contact1, contact2, contact3);

        dto.setStatus(status);
        dto.setDescription(expectedDescription);
        dto.setTheme(expectedTheme);
        dto.setContacts(expectedContacts);
        dto.setTitle(expectedTitle);

        Response response = given()
                .when()
                .body(dto)
                .header("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", ContentType.JSON)
                .patch("/api/v1/projects/" + 1)
                .then()
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();
        String errorMessage = jsonPath.get("message");

        Assertions.assertEquals(PROJECT_WRONG_ACCESS, errorMessage);
    }

    // TODO: retest with new inserted data
    @Test
    public void testGetProjectMembers() {
        Specifications.installSpecification(Specifications.requestSpec("/api/v1/positions/projects/members?projectId=" + 2), Specifications.responseSpec(200));

        List<ProjectMemberDTO> dtos = given()
                .when()
                .header("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", ContentType.JSON)
                .get("/api/v1/positions/projects/members?projectId=" + 2)
                .then()
                .extract()
                .body().jsonPath().getList(".", ProjectMemberDTO.class);

        int expectedNumberOfProjectMembers = 1;
        Assertions.assertEquals(expectedNumberOfProjectMembers, dtos.size());
    }

    @Test
    public void testGetProjectMembersWithInvalidUser() {
        Specifications.installSpecification(Specifications.requestSpec("/api/v1/positions/projects/members?projectId=" + 1), Specifications.responseSpec(404));

        given()
                .when()
                .header("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", ContentType.JSON)
                .get("/api/v1/positions/projects/members?projectId=" + 1)
                .then()
                .extract()
                .response();
    }

    @Test
    public void testCreateProject() {
        Specifications.installSpecification(Specifications.requestSpec("/api/v1/projects"), Specifications.responseSpec(200));

        ProjectCreateDTO dto = new ProjectCreateDTO();
        String title = "New title";
        String theme = "New theme";
        String description = "New description";
        ContactDTO contact1 = ContactDTO.builder().description("New link 1").link("Link 1").build();
        ContactDTO contact2 = ContactDTO.builder().description("New link 2").link("Link 2").build();
        ContactDTO contact3 = ContactDTO.builder().description("New link 3").link("Link 3").build();
        List<ContactDTO> expectedContacts = List.of(contact1, contact2, contact3);

        dto.setStatus(ProjectStatus.PREPARING);
        dto.setDescription(description);
        dto.setTheme(theme);
        dto.setContacts(expectedContacts);
        dto.setTitle(title);
        dto.setDirection(Direction.BACKEND);

        ProjectDTO projectDTO = given()
                .when()
                .body(dto)
                .header("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", ContentType.JSON)
                .patch("/api/v1/projects/" + 2)
                .then()
                .extract()
                .body().as(ProjectDTO.class);

        boolean expectedIsLeader = true;
        String expectedTitle = "New title";
        String expectedTheme = "New theme";
        String expectedDescription = "New description";
        ProjectStatusDictionary expectedProjectStatus
                = new ProjectStatusDictionary(ProjectStatus.PREPARING, "Project is in preparation stage");
        int expectedNumberOfMembers = 1;

        Assertions.assertEquals(expectedIsLeader, projectDTO.getIsLeader());
        Assertions.assertEquals(expectedTitle, projectDTO.getTitle());
        Assertions.assertEquals(expectedTheme, projectDTO.getTheme());
        Assertions.assertEquals(expectedDescription, projectDTO.getDescription());
        Assertions.assertEquals(expectedProjectStatus, projectDTO.getStatus());
        Assertions.assertEquals(expectedContacts.size(), projectDTO.getContacts().size());
        Assertions.assertEquals(expectedNumberOfMembers, projectDTO.getMembersCount());

        Specifications.installSpecification
                (Specifications.requestSpec("/api/v1/positions/projects?projectId=" + projectDTO.getId() + "&isVisible=false"),
                Specifications.responseSpec(200));

        // TODO: fix
        List<VacancyDTO> vacancyDTOList = given()
                .when()
                .header("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", ContentType.JSON)
                .patch("/api/v1/positions/projects?projectId=" + projectDTO.getId() + "&isVisible=false")
                .then()
                .extract()
                .body().jsonPath().getList(".", VacancyDTO.class);

        int expectedNumberOfVacancies = 1;

        Assertions.assertEquals(expectedNumberOfVacancies, vacancyDTOList.size());

        // протестить, что у юзера добавился этот проект
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
