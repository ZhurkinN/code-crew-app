package cis.tinkoff.controller;

import cis.tinkoff.model.Project;
import cis.tinkoff.service.ProjectService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;

import java.util.List;

@Tag(name = "Projects", description = "All actions with projects.")
@Controller("/projects")
public class ProjectController {

    @Inject
    private ProjectService projectService;

    @Operation(method = "findAll", description = "Finds all projects")
    @Get(produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<Project>> findAll() {
        return HttpResponse.ok(projectService.getAll());
    }
}
