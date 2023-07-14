package cis.tinkoff.rest;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class AbstractIntegrationTest {

    @Container
    public static PostgreSQLContainer postgresContainer;

    static {
        postgresContainer
            = new PostgreSQLContainer("postgres:14");
        postgresContainer.start();
    }
}
