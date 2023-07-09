package cis.tinkoff.controller;

import cis.tinkoff.controller.model.ProjectDTO;
import cis.tinkoff.service.ProjectService;
import cis.tinkoff.support.exceptions.InaccessibleActionException;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
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

    @Operation(method = "getAllUserProjects", description = "Finds all user projects")
    @Get(produces = MediaType.APPLICATION_JSON)
    public HttpResponse<?> getAllUserProjects(
            Authentication authentication,
            @Nullable @QueryValue(value = "lead") Boolean isLead
    ) {
        List<ProjectDTO> projectDTOList = projectService.getAllUserProjects(
                authentication.getName(),
                isLead);

        return HttpResponse.ok(projectDTOList);
    }

    @Operation(method = "getProjectById", description = "Find project by id")
    @Get(value = "/{id}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<?> getProjectById(
            Authentication authentication,
            @PathVariable(value = "id") Long id) {
        ProjectDTO projectDTO = projectService.getProjectById(id, authentication.getName());

        return HttpResponse.ok(projectDTO);
    }

    @Operation(method = "deleteProjectById", description = "Soft delete project by id")
    @Delete(value = "/{id}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<?> deleteProjectById(
            Authentication authentication,
            @PathVariable(value = "id") Long id) throws InaccessibleActionException {
        projectService.deleteProjectById(id, authentication.getName());

        return HttpResponse.ok();
    }

    @Operation(method = "leaveUserFromProject", description = "Leave from project")
    @Post(value = "/leave/{id}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<?> leaveUserFromProject(
            Authentication authentication,
            @PathVariable(value = "id") Long id) throws Exception {
        projectService.leaveUserFromProject(id, authentication.getName());

        return HttpResponse.ok();
    }
}
