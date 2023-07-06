package cis.tinkoff.controller;

import cis.tinkoff.model.Project;
import cis.tinkoff.service.ProjectService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Tag(name = "Projects", description = "All actions with projects.")
@Controller("/api/v1/projects")
@Secured(SecurityRule.IS_AUTHENTICATED)
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @Operation(method = "findAll", description = "Finds all projects")
    @Get(produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<Project>> findAll() {
        return HttpResponse.ok(projectService.getAll());
    }
}
