package cis.tinkoff.rest;

import cis.tinkoff.controller.model.ProjectDTO;
import cis.tinkoff.controller.model.ResumeDTO;
import cis.tinkoff.model.DirectionDictionary;
import cis.tinkoff.model.Project;
import cis.tinkoff.model.Resume;
import cis.tinkoff.model.User;
import cis.tinkoff.model.enumerated.Direction;
import cis.tinkoff.rest.model.UserLoginDTO;
import cis.tinkoff.spec.Specifications;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

@MicronautTest
@Testcontainers
public class RESTResumeTest {

    @Container
    public static TestSQLContainer container = TestSQLContainer.getInstance();

    private static String TOKEN = "";

    @BeforeAll
    static void setUp() {
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

    @BeforeEach
    void startContainer() {
        container.start();
    }

    @AfterEach
    void stopContainer() {
        container.stop();
    }

    @Test
    public void testFindById() {
        Specifications.installSpecification(Specifications.requestSpec("/api/v1/resumes/1"), Specifications.responseSpec(200));

        ResumeDTO dto = given()
                .when()
                .header("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", ContentType.JSON)
                .get("/api/v1/resumes/1")
                .then()
                .extract()
                .body().as(ResumeDTO.class);

        Long expectedId = 1L;
        String expectedDescription = "Hey, i want to work backend dev. Wanna try new technologies and new ideas. Open to new contacts";
        Long expectedUserId = 1L;
        List<String> expectedSkills = List.of("java", "spring", "micronaut", "docker", "postgres", "rest");
        DirectionDictionary expectedDirection
                = new DirectionDictionary(Direction.BACKEND, "Backend-developer");

        Assertions.assertEquals(expectedId, dto.getId());
        Assertions.assertEquals(expectedDescription, dto.getDescription());
        Assertions.assertEquals(expectedUserId, dto.getUser().getId());
        Assertions.assertEquals(expectedSkills, dto.getSkills());
        Assertions.assertEquals(expectedDirection, dto.getDirection());
    }

    @Test
    public void testFindUsersResumes() {
        Specifications.installSpecification(Specifications.requestSpec("/api/v1/resumes"), Specifications.responseSpec(200));

        List<ResumeDTO> resumes = given()
                .when()
                .header("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", ContentType.JSON)
                .get("/api/v1/resumes")
                .then()
                .extract()
                .body().jsonPath().getList(".", ResumeDTO.class);

        List<ResumeDTO> expectedResumes = new ArrayList<>();

        ResumeDTO expectedDTO1 = new ResumeDTO();
        expectedDTO1.setId(1L);
        expectedDTO1.setDirection(new DirectionDictionary(Direction.BACKEND, "Backend-developer"));
        expectedDTO1.setDescription("Hey, i want to work backend dev. Wanna try new technologies and new ideas. Open to new contacts");
        expectedDTO1.setSkills(List.of("java", "spring", "micronaut", "docker", "postgres", "rest"));
        expectedDTO1.setIsActive(true);

        ResumeDTO expectedDTO2 = new ResumeDTO();
        expectedDTO2.setId(2L);
        expectedDTO2.setDirection(new DirectionDictionary(Direction.ML, "Machine Learning Engineer"));
        expectedDTO2.setDescription("Hey, i want to work Machine Learning engineer. Wanna learn new practices and write code on python");
        expectedDTO2.setSkills(List.of("python"));
        expectedDTO2.setIsActive(true);

        Specifications.installSpecification(Specifications.requestSpec("/api/v1/users/" + 1), Specifications.responseSpec(200));

        Response response = given()
                .when()
                .header("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", ContentType.JSON)
                .get("/api/v1/users/" + 1)
                .then()
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();
        Long id = 1L;
        Long createdWhen = jsonPath.get("createdWhen");
        boolean isDeleted = jsonPath.get("isDeleted");
        String name = jsonPath.get("name");
        String surname = jsonPath.getString("surname");
        String email = jsonPath.get("email");
        List<String> contacts = jsonPath.get("contacts");
        String mainInformation = jsonPath.get("mainInformation");

        User expectedUser = new User();
        expectedUser.setId(id);
        expectedUser.setCreatedWhen(createdWhen);
        expectedUser.setIsDeleted(isDeleted);
        expectedUser.setEmail(email);
        expectedUser.setName(name);
        expectedUser.setSurname(surname);
        expectedUser.setMainInformation(mainInformation);
        expectedUser.setContacts(contacts);

        expectedDTO1.setUser(expectedUser);
        expectedDTO2.setUser(expectedUser);

        expectedResumes.add(expectedDTO1);
        expectedResumes.add(expectedDTO2);

        Assertions.assertEquals(expectedResumes.size(), resumes.size());

        for (int i = 0; i < resumes.size(); i++) {
            Assertions.assertEquals(expectedResumes.get(i), resumes.get(i));
        }
    }

    @Test
    public void testFindUsersResumeWithoutAuth() {

    }
}
