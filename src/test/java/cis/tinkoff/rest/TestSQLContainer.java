package cis.tinkoff.rest;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class TestSQLContainer extends PostgreSQLContainer {

//    @Container
//    public static PostgreSQLContainer postgresContainer;

//    static {
//        postgresContainer
//            = new PostgreSQLContainer("postgres:14");
//        postgresContainer.start();
//    }

    private static TestSQLContainer container;

    private TestSQLContainer() {
        super("postgres:14");
    }

    public static TestSQLContainer getInstance() {
        if (container == null) {
            container = new TestSQLContainer();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }
}
