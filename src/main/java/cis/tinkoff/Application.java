package cis.tinkoff;

import io.micronaut.context.ApplicationContextBuilder;
import io.micronaut.context.ApplicationContextConfigurer;
import io.micronaut.context.annotation.ContextConfigurer;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.h2.tools.Server;

import java.sql.SQLException;

@OpenAPIDefinition(
        info = @Info(
                title = "CodeCrew - WEB service for finding development teams",
                description = "The service allows you to create and find a dream team for you!",
                version = "0.1",
                contact = @Contact(
                        name = "CodeCrewTeam",
                        email = "zhurkin236@gmail.com"
                )
        )
)
public class Application {

    @ContextConfigurer
    public static class Configurer implements ApplicationContextConfigurer {
        @Override
        public void configure(@NonNull ApplicationContextBuilder builder) {
            builder.defaultEnvironments("dev");
        }
    }

    public static void main(String[] args) throws SQLException {
        Server.createWebServer().start();
        Micronaut.run(Application.class, args);
    }
}
