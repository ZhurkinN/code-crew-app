package cis.tinkoff.controller;

import cis.tinkoff.controller.model.ProjectCreateDTO;
import cis.tinkoff.controller.model.ProjectDTO;
import cis.tinkoff.model.Project;
import cis.tinkoff.model.enumerated.Direction;
import cis.tinkoff.service.ProjectService;
import cis.tinkoff.support.exceptions.InaccessibleActionException;
import cis.tinkoff.support.exceptions.RecordNotFoundException;
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
            @PathVariable(value = "id") Long id) throws InaccessibleActionException, RecordNotFoundException {
        projectService.deleteProjectById(id, authentication.getName());

        return HttpResponse.ok();
    }

    @Operation(method = "leaveUserFromProject", description = "Leave from project")
    @Post(value = "/leave/{id}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<?> leaveUserFromProject(
            Authentication authentication,
            @PathVariable(value = "id") Long id,
            @Nullable @QueryValue(value = "newLeaderId") Long newLeaderId) throws Exception {
        projectService.leaveUserFromProject(id, authentication.getName(), newLeaderId);

        return HttpResponse.ok();
    }

    @Operation(method = "deleteUserFromProject", description = "Delete user from project")
    @Post(value = "/{id}/delete-user", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<?> deleteUserFromProject(
            Authentication authentication,
            @PathVariable(name = "id") Long id,
            @QueryValue(value = "userId") Long userId,
            @QueryValue(value = "direction") Direction direction
    ) throws RecordNotFoundException, InaccessibleActionException {
        ProjectDTO projectDTO = projectService.deleteUserFromProject(id, authentication.getName(), userId, direction);

        return HttpResponse.ok(projectDTO);
    }

    @Operation(method = "createProject", description = "Delete user from project")
    @Post(produces = MediaType.APPLICATION_JSON)
    public HttpResponse<?> createProject(
            Authentication authentication,
            @Body ProjectCreateDTO projectCreateDTO
    ) throws RecordNotFoundException {
        Long id = projectService.createProject(authentication.getName(), projectCreateDTO);

        return HttpResponse.ok();
    }

    @Operation(method = "updateProject", description = "Update project information")
    @Patch(value = "/{id}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<?> updateProject(
            Authentication authentication,
            @PathVariable(name = "id") Long id,
            @Body Project projectForUpdate
    ) throws RecordNotFoundException, InaccessibleActionException {
        ProjectDTO project = projectService.updateProject(id, authentication.getName(), projectForUpdate);

        return HttpResponse.ok(project);
    }
}
