package cis.tinkoff.rest;

import cis.tinkoff.controller.model.ResumeDTO;
import cis.tinkoff.controller.model.custom.InteractResumeDTO;
import cis.tinkoff.controller.model.custom.RequestsChoiceResumeDTO;
import cis.tinkoff.controller.model.custom.SearchDTO;
import cis.tinkoff.model.DirectionDictionary;
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

    private static String TOKEN_1 = "";

    private static String TOKEN_2 = "";

    @BeforeAll
    static void setUp() {
        RestAssured.defaultParser = Parser.JSON;
        UserLoginDTO dto1 = new UserLoginDTO("alex@mail.ru", "123");

        Specifications.installSpecification(Specifications.requestSpec("/auth/login"), Specifications.responseSpec(200));

        TOKEN_1 = given()
                .urlEncodingEnabled(false)
                .body(dto1)
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

//    @BeforeEach
//    void startContainer() {
//        container.start();
//    }
//
//    @AfterEach
//    void stopContainer() {
//        container.stop();
//    }

    @Test
    public void testFindById() {
        Specifications.installSpecification(Specifications.requestSpec("/api/v1/resumes/1"), Specifications.responseSpec(200));

        ResumeDTO dto = given()
                .when()
                .header("Authorization", "Bearer " + TOKEN_1)
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
        Specifications.installSpecification(Specifications.requestSpec("/api/v1/resumes?isActive=true"), Specifications.responseSpec(200));

        List<ResumeDTO> resumes = given()
                .when()
                .header("Authorization", "Bearer " + TOKEN_1)
                .header("Content-Type", ContentType.JSON)
                .get("/api/v1/resumes?isActive=true")
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
                .header("Authorization", "Bearer " + TOKEN_1)
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
    public void testUpdate() {
        Specifications.installSpecification(Specifications.requestSpec("/api/v1/resumes/1"), Specifications.responseSpec(200));

        String expectedDescription = "New description";
        List<String> expectedSkills = List.of("Skill 1", "Skill 2", "Skill 3");
        String direction = "ML";
        DirectionDictionary expectedDirection = new DirectionDictionary(Direction.ML, "Machine Learning Engineer");
        InteractResumeDTO updateDTO = new InteractResumeDTO(expectedDescription, expectedSkills, direction);

        ResumeDTO dto = given()
                .body(updateDTO)
                .when()
                .header("Authorization", "Bearer " + TOKEN_1)
                .header("Content-Type", ContentType.JSON)
                .patch("/api/v1/resumes/1")
                .then()
                .extract()
                .body().as(ResumeDTO.class);

        Assertions.assertEquals(expectedDescription, dto.getDescription());
        Assertions.assertEquals(expectedSkills, dto.getSkills());
        Assertions.assertEquals(expectedDirection, dto.getDirection());
    }

    @Test
    public void testUpdateWithInvalidUser() {
        Specifications.installSpecification(Specifications.requestSpec("/api/v1/resumes/3"), Specifications.responseSpec(406));

        String expectedDescription = "New description";
        List<String> expectedSkills = List.of("Skill 1", "Skill 2", "Skill 3");
        String direction = "DEVOPS";
        DirectionDictionary expectedDirection = new DirectionDictionary(Direction.DEVOPS, "DevOps Engineer");
        InteractResumeDTO updateDTO = new InteractResumeDTO(expectedDescription, expectedSkills, direction);

        Response response = given()
                .body(updateDTO)
                .when()
                .header("Authorization", "Bearer " + TOKEN_1)
                .header("Content-Type", ContentType.JSON)
                .patch("/api/v1/resumes/3")
                .then()
                .extract()
                .response();
    }

    @Test
    public void testUpdateWithInvalidDirection() {
        Specifications.installSpecification(Specifications.requestSpec("/api/v1/resumes/3"), Specifications.responseSpec(409));

        String expectedDescription = "New description";
        List<String> expectedSkills = List.of("Skill 1", "Skill 2", "Skill 3");
        String direction = "BAD_DIRECTION";
        InteractResumeDTO updateDTO = new InteractResumeDTO(expectedDescription, expectedSkills, direction);

        Response response = given()
                .body(updateDTO)
                .when()
                .header("Authorization", "Bearer " + TOKEN_1)
                .header("Content-Type", ContentType.JSON)
                .patch("/api/v1/resumes/1")
                .then()
                .extract()
                .response();
    }

    @Test
    public void testChangeActivity() {
        Specifications.installSpecification(Specifications.requestSpec("/api/v1/resumes/1"), Specifications.responseSpec(200));

        ResumeDTO dto = given()
                .when()
                .header("Authorization", "Bearer " + TOKEN_1)
                .header("Content-Type", ContentType.JSON)
                .get("/api/v1/resumes/1")
                .then()
                .extract()
                .body().as(ResumeDTO.class);

        Long expectedId = dto.getId();
        DirectionDictionary expectedDirection = dto.getDirection();
        String expectedDescription = dto.getDescription();
        List<String> expectedSkills = dto.getSkills();
        Long expectedCreatedWhen = dto.getCreatedWhen();
        boolean expectedIsActive = !dto.getIsActive();

        Specifications.installSpecification(Specifications.requestSpec("/api/v1/resumes/active/1"), Specifications.responseSpec(200));

        dto = given()
                .when()
                .header("Authorization", "Bearer " + TOKEN_1)
                .header("Content-Type", ContentType.JSON)
                .post("/api/v1/resumes/active/1")
                .then()
                .extract()
                .body().as(ResumeDTO.class);

        Assertions.assertEquals(expectedId, dto.getId());
//        Assertions.assertEquals(expectedDirection, dto.getDirection());
        Assertions.assertEquals(expectedDescription, dto.getDescription());
        Assertions.assertEquals(expectedSkills, dto.getSkills());
        Assertions.assertEquals(expectedCreatedWhen, dto.getCreatedWhen());
        Assertions.assertEquals(expectedIsActive, dto.getIsActive());

        Specifications.installSpecification(Specifications.requestSpec("/api/v1/resumes/active/1"), Specifications.responseSpec(200));

        given()
                .when()
                .header("Authorization", "Bearer " + TOKEN_1)
                .header("Content-Type", ContentType.JSON)
                .post("/api/v1/resumes/active/1")
                .then()
                .extract()
                .body().as(ResumeDTO.class);
    }

    @Test
    public void testCreateResume() {
        Specifications.installSpecification(Specifications.requestSpec("/api/v1/resumes"), Specifications.responseSpec(200));

        String expectedDescription = "New description";
        List<String> expectedSkills = List.of("Skill 1", "Skill 2", "Skill 3");
        String direction = "ML";
        DirectionDictionary expectedDirection = new DirectionDictionary(Direction.ML, "Machine Learning Engineer");
        InteractResumeDTO updateDTO = new InteractResumeDTO(expectedDescription, expectedSkills, direction);
        boolean expectedIsActive = true;

        ResumeDTO dto = given()
                .body(updateDTO)
                .when()
                .header("Authorization", "Bearer " + TOKEN_2)
                .header("Content-Type", ContentType.JSON)
                .post("/api/v1/resumes")
                .then()
                .extract()
                .body().as(ResumeDTO.class);

        Assertions.assertEquals(expectedDirection, dto.getDirection());
        Assertions.assertEquals(expectedDescription, dto.getDescription());
        Assertions.assertEquals(expectedSkills, dto.getSkills());
        Assertions.assertEquals(expectedIsActive, dto.getIsActive());
        Assertions.assertNotNull(dto.getCreatedWhen());
    }

    @Test
    public void testFindUsersRequestsChoiceResumes() {
        Specifications.installSpecification(Specifications.requestSpec("/api/v1/resumes/active"), Specifications.responseSpec(200));

        DirectionDictionary expectedDirection1 = new DirectionDictionary(Direction.BACKEND, "Backend-developer");
        DirectionDictionary expectedDirection2 = new DirectionDictionary(Direction.ML, "Machine Learning Engineer");
        List<DirectionDictionary> directions = List.of(expectedDirection1, expectedDirection2);


        List<RequestsChoiceResumeDTO> resumes = given()
                .when()
                .header("Authorization", "Bearer " + TOKEN_1)
                .header("Content-Type", ContentType.JSON)
                .get("/api/v1/resumes/active")
                .then()
                .extract()
                .body().jsonPath().getList(".", RequestsChoiceResumeDTO.class);

        Assertions.assertEquals(directions.size(), resumes.size());

        for (int i = 0; i < directions.size(); i++) {
            Assertions.assertEquals(directions.get(i), resumes.get(i).direction());
        }
    }

    @Test
    public void testSearchBySkills() {
        Specifications.installSpecification(Specifications.requestSpec("/api/v1/resumes/search?size=10&skills=java"), Specifications.responseSpec(200));

        List<ResumeDTO> dto = given()
                .when()
                .header("Authorization", "Bearer " + TOKEN_2)
                .header("Content-Type", ContentType.JSON)
                .get("/api/v1/resumes/search?size=10&skills=java")
                .then()
                .extract()
                .body().jsonPath().getList("content.", ResumeDTO.class);

        int expectedSize = 3;
        Assertions.assertEquals(expectedSize, dto.size());

        for (int i = 0; i < expectedSize; i++) {
            Assertions.assertTrue(dto.get(i).getSkills().contains("java"));
        }
    }

    @Test
    public void shouldReturnResumesWhenPassingSkillInDifferentCase() {
        Specifications.installSpecification(Specifications.requestSpec("/api/v1/resumes/search?size=10&skills=jaVA"), Specifications.responseSpec(200));

        List<ResumeDTO> dto = given()
                .when()
                .header("Authorization", "Bearer " + TOKEN_2)
                .header("Content-Type", ContentType.JSON)
                .get("/api/v1/resumes/search?size=10&skills=jaVaScrIpt")
                .then()
                .extract()
                .body().jsonPath().getList("content.", ResumeDTO.class);

        int expectedSize = 3;
        Assertions.assertEquals(expectedSize, dto.size());

        for (int i = 0; i < expectedSize; i++) {
            Assertions.assertTrue(dto.get(i).getSkills().contains("java"));
        }
    }

    @Test
    public void testSearchByDirection() {
        Specifications.installSpecification(Specifications.requestSpec("/api/v1/resumes/search?size=10&direction=FRONTEND"), Specifications.responseSpec(200));

        int expectedSize = 2;
        DirectionDictionary expectedDirection = new DirectionDictionary(Direction.FRONTEND, "Frontend-developer");

        List<ResumeDTO> dto = given()
                .when()
                .header("Authorization", "Bearer " + TOKEN_2)
                .header("Content-Type", ContentType.JSON)
                .get("/api/v1/resumes/search?size=10&direction=FRONTEND")
                .then()
                .extract()
                .body().jsonPath().getList("content.", ResumeDTO.class);

        Assertions.assertEquals(expectedSize, dto.size());

        for (int i = 0; i < expectedSize; i++) {
            Assertions.assertEquals(expectedDirection, dto.get(i).getDirection());
        }
    }

    @Test
    public void testSearchByDate() {
        Specifications.installSpecification(Specifications.requestSpec("/api/v1/resumes/search?size=10&dateSort=ASC"), Specifications.responseSpec(200));

        List<ResumeDTO> dto = given()
                .when()
                .header("Authorization", "Bearer " + TOKEN_2)
                .header("Content-Type", ContentType.JSON)
                .get("api/v1/resumes/search?size=10&dateSort=ASC")
                .then()
                .extract()
                .body().jsonPath().getList("content.", ResumeDTO.class);

        int expectedSize = 10;
        int resumeListSize = dto.size();

        Assertions.assertEquals(expectedSize, resumeListSize);

        ResumeDTO penultimateResume = dto.get(resumeListSize - 2);
        ResumeDTO lastResume = dto.get(resumeListSize - 1);

        Assertions.assertTrue(penultimateResume.getCreatedWhen() < lastResume.getCreatedWhen());
    }

    @Test
    @AfterAll
    public static void testSoftDelete() {
        Specifications.installSpecification(Specifications.requestSpec("/api/v1/resumes?isActive=true"), Specifications.responseSpec(200));

        List<ResumeDTO> resumes = given()
                .when()
                .header("Authorization", "Bearer " + TOKEN_1)
                .header("Content-Type", ContentType.JSON)
                .get("/api/v1/resumes?isActive=true")
                .then()
                .extract()
                .body().jsonPath().getList(".", ResumeDTO.class);

        int expectedResumesSize = resumes.size() - 1;

        Specifications.installSpecification(Specifications.requestSpec("/api/v1/resumes/1"), Specifications.responseSpec(200));

        given()
                .when()
                .header("Authorization", "Bearer " + TOKEN_1)
                .header("Content-Type", ContentType.JSON)
                .delete("/api/v1/resumes/1")
                .then()
                .log()
                .all();

        Specifications.installSpecification(Specifications.requestSpec("/api/v1/resumes?isActive=true"), Specifications.responseSpec(200));

        resumes = given()
                .when()
                .header("Authorization", "Bearer " + TOKEN_1)
                .header("Content-Type", ContentType.JSON)
                .get("/api/v1/resumes?isActive=true")
                .then()
                .extract()
                .body().jsonPath().getList(".", ResumeDTO.class);

        Assertions.assertEquals(expectedResumesSize, resumes.size());
    }
}
