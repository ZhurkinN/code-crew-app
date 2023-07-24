package cis.tinkoff.rest;

import cis.tinkoff.controller.model.PositionRequestDTO;
import cis.tinkoff.controller.model.ProjectDTO;
import cis.tinkoff.controller.model.UserDTO;
import cis.tinkoff.controller.model.custom.CreateRequestDTO;
import cis.tinkoff.model.Position;
import cis.tinkoff.model.RequestStatusDictionary;
import cis.tinkoff.model.Resume;
import cis.tinkoff.model.enumerated.RequestStatus;
import cis.tinkoff.rest.model.UserLoginDTO;
import cis.tinkoff.spec.Specifications;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static cis.tinkoff.support.exceptions.constants.ErrorDisplayMessageKeeper.INACCESSIBLE_POSITION_ACTION;
import static io.restassured.RestAssured.given;

@MicronautTest
@Testcontainers
public class RESTRequestTest {

    @Container
    public static TestSQLContainer container = TestSQLContainer.getInstance();

    private static String TOKEN = "";
    private static String USER_MAIL = "alex@mail.ru";
    private static String USER_PASSWORD = "123";

    @BeforeAll
    static void setUp() {
        RestAssured.defaultParser = Parser.JSON;
        UserLoginDTO dto = new UserLoginDTO(USER_MAIL, USER_PASSWORD);

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
    public void testGetIncomingPositionRequests() {
        Specifications.installSpecification(Specifications.requestSpec("/api/v1/requests/vacancies/5?requestType=INCOMING"), Specifications.responseSpec(200));

        List<PositionRequestDTO> requests = given()
                .when()
                .header("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", ContentType.JSON)
                .get("/api/v1/requests/vacancies/5?requestType=INCOMING")
                .then()
                .extract()
                .body().jsonPath().getList(".", PositionRequestDTO.class);

        int expectedRequestsSize = 1;
        Long expectedResumeId = 8L;
        boolean expectedIsInvite = false;
        RequestStatusDictionary expectedStatus = new RequestStatusDictionary(RequestStatus.IN_CONSIDERATION, "Request is under consideration");

        PositionRequestDTO expectedDTO1 = new PositionRequestDTO()
                .setStatus(expectedStatus)
                .setIsInvite(expectedIsInvite)
                .setResume((Resume) new Resume().setId(expectedResumeId));

        List<PositionRequestDTO> expectedDTOS = List.of(expectedDTO1);

        Assertions.assertEquals(expectedRequestsSize, requests.size());

        for (int i = 0; i < expectedRequestsSize; i++) {
            PositionRequestDTO expectedDTO = expectedDTOS.get(i);
            PositionRequestDTO factDTO = requests.get(i);
            Assertions.assertNotNull(requests.get(i).getCoverLetter());
            Assertions.assertEquals(expectedDTO.getStatus(), factDTO.getStatus());
            Assertions.assertEquals(expectedDTO.getIsInvite(), factDTO.getIsInvite());
            Assertions.assertEquals(expectedDTO.getResume().getId(), factDTO.getResume().getId());
        }
    }

    @Test
    public void shouldReturn406WhenGettingRequestsOnProjectByNotLead() {
        Specifications.installSpecification(Specifications.requestSpec("/api/v1/requests/vacancies/2?requestType=INCOMING"), Specifications.responseSpec(406));

        Response response = given()
                .when()
                .header("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", ContentType.JSON)
                .get("/api/v1/requests/vacancies/2?requestType=INCOMING")
                .then()
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();
        String errorMessage = jsonPath.get("message");

        Assertions.assertEquals(String.format(INACCESSIBLE_POSITION_ACTION, USER_MAIL, 2), errorMessage);
    }

    @Test
    public void testGetSentPositionRequests() {
        Specifications.installSpecification(Specifications.requestSpec("/api/v1/requests/vacancies/5?requestType=SENT"), Specifications.responseSpec(200));

        List<PositionRequestDTO> requests = given()
                .when()
                .header("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", ContentType.JSON)
                .get("/api/v1/requests/vacancies/5?requestType=SENT")
                .then()
                .extract()
                .body().jsonPath().getList(".", PositionRequestDTO.class);

        int expectedRequestsSize = 1;
        Long expectedResumeId = 2L;
        boolean expectedIsInvite = true;
        RequestStatusDictionary expectedStatus = new RequestStatusDictionary(RequestStatus.IN_CONSIDERATION, "Request is under consideration");

        PositionRequestDTO expectedDTO1 = new PositionRequestDTO()
                .setStatus(expectedStatus)
                .setIsInvite(expectedIsInvite)
                .setResume((Resume) new Resume().setId(expectedResumeId));

        List<PositionRequestDTO> expectedDTOS = List.of(expectedDTO1);

        Assertions.assertEquals(expectedRequestsSize, requests.size());

        for (int i = 0; i < expectedRequestsSize; i++) {
            PositionRequestDTO expectedDTO = expectedDTOS.get(i);
            PositionRequestDTO factDTO = requests.get(i);
            Assertions.assertNotNull(requests.get(i).getCoverLetter());
            Assertions.assertEquals(expectedDTO.getStatus(), factDTO.getStatus());
            Assertions.assertEquals(expectedDTO.getIsInvite(), factDTO.getIsInvite());
            Assertions.assertEquals(expectedDTO.getResume().getId(), factDTO.getResume().getId());
        }
    }

    @Test
    public void testGetResumeInvites() {
        Specifications.installSpecification(Specifications.requestSpec("/api/v1/requests/resumes/2?requestType=INCOMING"), Specifications.responseSpec(200));

        List<PositionRequestDTO> requests = given()
                .when()
                .header("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", ContentType.JSON)
                .get("/api/v1/requests/resumes/2?requestType=INCOMING")
                .then()
                .extract()
                .body().jsonPath().getList(".", PositionRequestDTO.class);

        int expectedRequestsSize = 2;
        Long expectedVacancyId = 2L;
        boolean expectedIsInvite = true;
        RequestStatusDictionary expectedStatus = new RequestStatusDictionary(RequestStatus.IN_CONSIDERATION, "Request is under consideration");

        PositionRequestDTO expectedDTO1 = new PositionRequestDTO()
                .setStatus(expectedStatus)
                .setIsInvite(expectedIsInvite)
                .setPosition((Position) new Position().setId(expectedVacancyId));

        expectedVacancyId = 5L;
        expectedStatus = new RequestStatusDictionary(RequestStatus.IN_CONSIDERATION, "Request is under consideration");

        PositionRequestDTO expectedDTO2 = new PositionRequestDTO()
                .setStatus(expectedStatus)
                .setIsInvite(expectedIsInvite)
                .setPosition((Position) new Position().setId(expectedVacancyId));

        List<PositionRequestDTO> expectedDTOS = List.of(expectedDTO1, expectedDTO2);

        Assertions.assertEquals(expectedRequestsSize, requests.size());

        for (int i = 0; i < expectedRequestsSize; i++) {
            PositionRequestDTO expectedDTO = expectedDTOS.get(i);
            PositionRequestDTO factDTO = requests.get(i);
            Assertions.assertNotNull(requests.get(i).getCoverLetter());
            Assertions.assertEquals(expectedDTO.getStatus(), factDTO.getStatus());
            Assertions.assertEquals(expectedDTO.getIsInvite(), factDTO.getIsInvite());
            Assertions.assertEquals(expectedDTO.getPosition().getId(), factDTO.getPosition().getId());
        }
    }

    @Test
    public void testGetRequestsSentWithResume() {
        Specifications.installSpecification(Specifications.requestSpec("/api/v1/requests/resumes/1?requestType=SENT"), Specifications.responseSpec(200));

        List<PositionRequestDTO> requests = given()
                .when()
                .header("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", ContentType.JSON)
                .get("/api/v1/requests/resumes/1?requestType=SENT")
                .then()
                .extract()
                .body().jsonPath().getList(".", PositionRequestDTO.class);

        int expectedRequestsSize = 2;
        Long expectedVacancyId = 1L;
        boolean expectedIsInvite = false;
        RequestStatusDictionary expectedStatus = new RequestStatusDictionary(RequestStatus.IN_CONSIDERATION, "Request is under consideration");

        PositionRequestDTO expectedDTO1 = new PositionRequestDTO()
                .setStatus(expectedStatus)
                .setIsInvite(expectedIsInvite)
                .setPosition((Position) new Position().setId(expectedVacancyId));

        expectedVacancyId = 14L;
        expectedStatus = new RequestStatusDictionary(RequestStatus.IN_CONSIDERATION, "Request is under consideration");

        PositionRequestDTO expectedDTO2 = new PositionRequestDTO()
                .setStatus(expectedStatus)
                .setIsInvite(expectedIsInvite)
                .setPosition((Position) new Position().setId(expectedVacancyId));

        List<PositionRequestDTO> expectedDTOS = List.of(expectedDTO1, expectedDTO2);

        Assertions.assertEquals(expectedRequestsSize, requests.size());

        for (int i = 0; i < expectedRequestsSize; i++) {
            PositionRequestDTO expectedDTO = expectedDTOS.get(i);
            PositionRequestDTO factDTO = requests.get(i);
            Assertions.assertNotNull(requests.get(i).getCoverLetter());
            Assertions.assertEquals(expectedDTO.getStatus(), factDTO.getStatus());
            Assertions.assertEquals(expectedDTO.getIsInvite(), factDTO.getIsInvite());
            Assertions.assertEquals(expectedDTO.getPosition().getId(), factDTO.getPosition().getId());
        }
    }

    @Test
    public void testCreatePositionRequest() {
        Long expectedVacancyId = 8L;
        Long expectedResumeId = 1L;
        String expectedCoverLetter = "Wanna work with you";

        Specifications.installSpecification(Specifications.requestSpec("/api/v1/requests/vacancies/8?requestType=INCOMING"), Specifications.responseSpec(200));

        UserLoginDTO loginDTO = new UserLoginDTO("kulich@anser.ru", "123");

        String token = given()
                .urlEncodingEnabled(false)
                .body(loginDTO)
                .when()
                .post("/auth/login")
                .then()
                .extract()
                .path("access_token");

        List<PositionRequestDTO> requests = given()
                .when()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", ContentType.JSON)
                .get("/api/v1/requests/vacancies/8?requestType=INCOMING")
                .then()
                .extract()
                .body().jsonPath().getList(".", PositionRequestDTO.class);

        Specifications.installSpecification(Specifications.requestSpec("/api/v1/requests/vacancies"), Specifications.responseSpec(200));

        CreateRequestDTO requestDTO = new CreateRequestDTO(expectedResumeId, expectedVacancyId, expectedCoverLetter);

        boolean expectedIsInvite = false;
        RequestStatusDictionary expectedStatus = new RequestStatusDictionary(RequestStatus.IN_CONSIDERATION, "Request is under consideration");

        PositionRequestDTO dto = given()
                .when()
                .body(requestDTO)
                .header("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", ContentType.JSON)
                .post("/api/v1/requests/vacancies")
                .then()
                .extract()
                .body().as(PositionRequestDTO.class);

        Specifications.installSpecification(Specifications.requestSpec("/api/v1/requests/vacancies/8?requestType=INCOMING"), Specifications.responseSpec(200));

        int expectedRequestsNumber = requests.size() + 1;

        requests = given()
                .when()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", ContentType.JSON)
                .get("/api/v1/requests/vacancies/8?requestType=INCOMING")
                .then()
                .extract()
                .body().jsonPath().getList(".", PositionRequestDTO.class);

        Assertions.assertEquals(expectedRequestsNumber, requests.size());
        Assertions.assertEquals(expectedVacancyId, dto.getPosition().getId());
        Assertions.assertEquals(expectedResumeId, dto.getResume().getId());
        Assertions.assertEquals(expectedCoverLetter, dto.getCoverLetter());
        Assertions.assertEquals(expectedStatus, dto.getStatus());
        Assertions.assertEquals(expectedIsInvite, dto.getIsInvite());
    }

    @Test
    public void testCreatePositionInvite() {
        Long expectedVacancyId = 10L;
        Long expectedResumeId = 8L;
        String expectedCoverLetter = "Wanna work with you";

        UserLoginDTO loginDTO = new UserLoginDTO("kio@mail.ru", "123");

        String token = given()
                .urlEncodingEnabled(false)
                .body(loginDTO)
                .when()
                .post("/auth/login")
                .then()
                .extract()
                .path("access_token");

        Specifications.installSpecification(Specifications.requestSpec("/api/v1/requests/vacancies/10?requestType=SENT"), Specifications.responseSpec(200));

        List<PositionRequestDTO> requests = given()
                .when()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", ContentType.JSON)
                .get("/api/v1/requests/vacancies/10?requestType=SENT")
                .then()
                .extract()
                .body().jsonPath().getList(".", PositionRequestDTO.class);

        Specifications.installSpecification(Specifications.requestSpec("/api/v1/requests/resumes"), Specifications.responseSpec(200));

        CreateRequestDTO requestDTO = new CreateRequestDTO(expectedResumeId, expectedVacancyId, expectedCoverLetter);

        boolean expectedIsInvite = true;
        RequestStatusDictionary expectedStatus = new RequestStatusDictionary(RequestStatus.IN_CONSIDERATION, "Request is under consideration");

        PositionRequestDTO dto = given()
                .when()
                .body(requestDTO)
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", ContentType.JSON)
                .post("/api/v1/requests/resumes")
                .then()
                .extract()
                .body().as(PositionRequestDTO.class);

        Specifications.installSpecification(Specifications.requestSpec("/api/v1/requests/vacancies/10?requestType=SENT"), Specifications.responseSpec(200));

        int expectedRequestsNumber = requests.size() + 1;

        requests = given()
                .when()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", ContentType.JSON)
                .get("/api/v1/requests/vacancies/10?requestType=SENT")
                .then()
                .extract()
                .body().jsonPath().getList(".", PositionRequestDTO.class);

        Assertions.assertEquals(expectedRequestsNumber, requests.size());
        Assertions.assertEquals(expectedVacancyId, dto.getPosition().getId());
        Assertions.assertEquals(expectedResumeId, dto.getResume().getId());
        Assertions.assertEquals(expectedCoverLetter, dto.getCoverLetter());
        Assertions.assertEquals(expectedStatus, dto.getStatus());
        Assertions.assertEquals(expectedIsInvite, dto.getIsInvite());
    }
}
