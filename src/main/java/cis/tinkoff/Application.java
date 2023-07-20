package cis.tinkoff;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

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

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }
}
