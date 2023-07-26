package cis.tinkoff.rest;

import cis.tinkoff.controller.model.UserDTO;
import cis.tinkoff.controller.model.custom.RegisterUserDTO;
import cis.tinkoff.rest.model.UserLoginDTO;
import cis.tinkoff.spec.Specifications;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static cis.tinkoff.support.exceptions.constants.LoggedErrorMessageKeeper.USER_ALREADY_EXISTS;
import static io.restassured.RestAssured.given;

@MicronautTest
@Testcontainers
public class RegistrationTest {

    @Container
    public static TestSQLContainer container = TestSQLContainer.getInstance();

    @Test
    public void testRegister() {
        String expectedName = "nikita";
        String expectedSurname = "brunov";
        String expectedEmail = "kukuipta@radick.rgrtu";
        String password = "123";
        RegisterUserDTO dto
                = new RegisterUserDTO(expectedEmail, password, expectedName, expectedSurname);

        Specifications.installSpecification(Specifications.requestSpec("/auth/login"), Specifications.responseSpec(401));

        UserLoginDTO loginDTO = new UserLoginDTO(expectedEmail, password);

        given()
                .urlEncodingEnabled(false)
                .body(loginDTO)
                .when()
                .post("/auth/login")
                .then()
                .extract()
                .path("access_token");

        Specifications.installSpecification(Specifications.requestSpec("/api/v1/users/register"), Specifications.responseSpec(200));

        UserDTO userDTO = given()
                .when()
                .body(dto)
                .header("Content-Type", ContentType.JSON)
                .post("/api/v1/users/register")
                .then()
                .extract()
                .body().as(UserDTO.class);

        Assertions.assertEquals(expectedEmail, userDTO.getEmail());
        Assertions.assertEquals(expectedName, userDTO.getName());
        Assertions.assertEquals(expectedSurname, userDTO.getSurname());

        Specifications.installSpecification(Specifications.requestSpec("/auth/login"), Specifications.responseSpec(200));

        String token = given()
                .urlEncodingEnabled(false)
                .body(loginDTO)
                .when()
                .post("/auth/login")
                .then()
                .extract()
                .path("access_token");

        Assertions.assertNotNull(token);
    }

    @Test
    public void testRegisterWithInvalidData() {
        Specifications.installSpecification(Specifications.requestSpec("/api/v1/users/register"), Specifications.responseSpec(409));

        RegisterUserDTO dto
                = new RegisterUserDTO(null, null, null, null);

        Response response = given()
                .when()
                .body(dto)
                .header("Content-Type", ContentType.JSON)
                .post("/api/v1/users/register")
                .then()
                .extract()
                .response();
    }

    @Test
    public void testRegisterWithExistingEmail() {
        Specifications.installSpecification(Specifications.requestSpec("/api/v1/users/register"), Specifications.responseSpec(409));

        String expectedName = "nikita";
        String expectedSurname = "brunov";
        String expectedEmail = "alex@mail.ru";
        String password = "123";
        RegisterUserDTO dto
                = new RegisterUserDTO(expectedEmail, password, expectedName, expectedSurname);

        Response response = given()
                .when()
                .body(dto)
                .header("Content-Type", ContentType.JSON)
                .post("/api/v1/users/register")
                .then()
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();
        String errorMessage = jsonPath.get("message");

        Assertions.assertEquals(String.format(USER_ALREADY_EXISTS, expectedEmail), errorMessage);
    }
}
