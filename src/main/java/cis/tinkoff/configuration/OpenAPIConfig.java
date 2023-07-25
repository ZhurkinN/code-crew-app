package cis.tinkoff.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "CodeCrew - WEB service for finding development teams",
                description = "The service allows you to create and find a dream team for you!",
                version = "1.0",
                contact = @Contact(
                        name = "CodeCrewTeam",
                        email = "zhurkin236@gmail.com"
                )
        )
)
public class OpenAPIConfig {

}
