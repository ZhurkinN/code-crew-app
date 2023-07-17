package cis.tinkoff.rest;

import cis.tinkoff.controller.model.custom.UpdateUserDTO;
import cis.tinkoff.model.Project;
import cis.tinkoff.model.Resume;
import cis.tinkoff.rest.model.UserLoginDTO;
import cis.tinkoff.spec.Specifications;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;
import static cis.tinkoff.support.exceptions.constants.ErrorDisplayMessageKeeper.*;

import java.util.List;

@MicronautTest
@Testcontainers
public class RESTUserTest {

    @Container
    public static TestSQLContainer container = TestSQLContainer.getInstance();

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
    @Order(1)
    public void testFindAllEndpoint() {
        Specifications.installSpecification(Specifications.requestSpec("/api/v1/users/all"), Specifications.responseSpec(200));

        Response response = given()
                .when()
                .header("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", ContentType.JSON)
                .get("/api/v1/users/all")
                .then()
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();
        List<String> emails = jsonPath.get("email");
        List<String> expectedEmails
                = List.of("alex@mail.ru", "weiber@mail.ru", "mercen@yandex.ru", "kulich@anser.ru",
                "reter@mail.ru", "kio@mail.ru", "alex@yandex.ru", "loire@mail.ru", "wesber@yandex.ru");

        Assertions.assertEquals(expectedEmails.size(), emails.size());
    }

    @Test
    @Order(2)
    public void testFindByIdEndpoint() {
        int userId = 3;
        Specifications.installSpecification(Specifications.requestSpec("/api/v1/users/" + userId), Specifications.responseSpec(200));

        Response response = given()
                .when()
                .header("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", ContentType.JSON)
                .get("/api/v1/users/" + userId)
                .then()
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();
        String name = jsonPath.get("name");
        String surname = jsonPath.getString("surname");
        String email = jsonPath.get("email");
        List<String> contacts = jsonPath.get("contacts");
        String mainInformation = jsonPath.get("mainInformation");
        List<Resume> resumes = jsonPath.getList("resumes");
        List<Project> projects = jsonPath.getList("projects");

        String expectedName = "Karl";
        String expectedSurname = "Lieben";
        String expectedEmail = "mercen@yandex.ru";
        List<String> expectedContacts = List.of("https://github.com/Lieben", "https://inst/Lieben");
        String expectedMainInformation = "I am Karl. Love to play basketball, watching films, doing programs, doing popcorn and smth else. Feeling kaif when popcorn is good. Feeling good when programs works";
        int expectedResumesSize = 1;
        int expectedProjectsSize = 1;

        Assertions.assertEquals(expectedName, name);
        Assertions.assertEquals(expectedSurname, surname);
        Assertions.assertEquals(expectedEmail, email);
        Assertions.assertEquals(expectedContacts, contacts);
        Assertions.assertEquals(expectedMainInformation, mainInformation);
        Assertions.assertEquals(expectedProjectsSize, projects.size());
        Assertions.assertEquals(expectedResumesSize, resumes.size());
    }

    @Test
    @Order(3)
    public void testFindEndpoint() {
        Specifications.installSpecification(Specifications.requestSpec("/api/v1/users"), Specifications.responseSpec(200));

        Response response = given()
                .when()
                .header("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", ContentType.JSON)
                .get("/api/v1/users")
                .then()
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();
        String name = jsonPath.get("name");
        String surname = jsonPath.getString("surname");
        String email = jsonPath.get("email");
        List<String> contacts = jsonPath.get("contacts");
        String mainInformation = jsonPath.get("mainInformation");
        List<Resume> resumes = jsonPath.getList("resumes");
        List<Project> projects = jsonPath.getList("projects");

        String expectedName = "Gordon";
        String expectedSurname = "Alonso";
        String expectedEmail = "alex@mail.ru";
        List<String> expectedContacts = List.of("https://github.com/Alonso", "https://inst/Alonso");
        String expectedMainInformation = "I am Gordon. Love to play basketball, watching films, doing programs, doing popcorn and smth else. Feeling kaif when popcorn is good. Feeling good when programs works";
        int expectedResumesSize = 1;
        int expectedProjectsSize = 1;

        Assertions.assertEquals(expectedName, name);
        Assertions.assertEquals(expectedSurname, surname);
        Assertions.assertEquals(expectedEmail, email);
        Assertions.assertEquals(expectedContacts, contacts);
        Assertions.assertEquals(expectedMainInformation, mainInformation);
        Assertions.assertEquals(expectedProjectsSize, projects.size());
        Assertions.assertEquals(expectedResumesSize, resumes.size());
    }

    @Test
    @Order(4)
    public void testUpdateEndpoint() {
        Specifications.installSpecification(Specifications.requestSpec("/api/v1/users"), Specifications.responseSpec(200));

        String expectedName = "Name";
        String expectedSurname = "Surname";
        List<String> expectedContacts = List.of("http://con1", "http://con2", "http://con3");
        String expectedPictureLink = "link";
        String expectedMainInformation = "I am new";

        UpdateUserDTO dto = new UpdateUserDTO(expectedName, expectedSurname, expectedContacts, expectedPictureLink, expectedMainInformation);

        given()
                .body(dto)
                .when()
                .header("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", ContentType.JSON)
                .patch("/api/v1/users")
                .then()
                .log()
                .all();

        Specifications.installSpecification(Specifications.requestSpec("/api/v1/users"), Specifications.responseSpec(200));

        Response response = given()
                .when()
                .header("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", ContentType.JSON)
                .get("/api/v1/users")
                .then()
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();
        String name = jsonPath.get("name");
        String surname = jsonPath.getString("surname");
        String pictureLink = jsonPath.getString("pictureLink");
        List<String> contacts = jsonPath.get("contacts");
        String mainInformation = jsonPath.get("mainInformation");

        Assertions.assertEquals(expectedName, name);
        Assertions.assertEquals(expectedSurname, surname);
        Assertions.assertEquals(expectedContacts, contacts);
        Assertions.assertEquals(expectedMainInformation, mainInformation);
    }

    @Test
    @Order(5)
    public void testUpdateEndpointWithInvalidData() {
        Specifications.installSpecification(Specifications.requestSpec("/api/v1/users"), Specifications.responseSpec(409));

        UpdateUserDTO dto = new UpdateUserDTO(null, null, null, null, null);

        given()
                .body(dto)
                .when()
                .header("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", ContentType.JSON)
                .patch("/api/v1/users")
                .then()
                .log()
                .all();
    }

    @Test
    @AfterAll
    public static void testSoftDeleteEndpoint() {
        Specifications.installSpecification(Specifications.requestSpec("/api/v1/users"), Specifications.responseSpec(200));

        given()
                .when()
                .header("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", ContentType.JSON)
                .delete("/api/v1/users")
                .then()
                .log()
                .all();

        Specifications.installSpecification(Specifications.requestSpec("/api/v1/users/" + 1), Specifications.responseSpec(404));

        Response response = given()
                .when()
                .header("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", ContentType.JSON)
                .get("/api/v1/users/" + 1)
                .then()
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();
        String errorMessage = jsonPath.get("message");

        Assertions.assertEquals(USER_NOT_FOUND, errorMessage);
    }
}
