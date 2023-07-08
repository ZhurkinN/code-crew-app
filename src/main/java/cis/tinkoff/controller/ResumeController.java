package cis.tinkoff.controller;

import cis.tinkoff.controller.model.CreateResumeDTO;
import cis.tinkoff.model.Resume;
import cis.tinkoff.model.User;
import cis.tinkoff.service.ResumeService;
import cis.tinkoff.service.UserService;
import cis.tinkoff.support.exceptions.DeletedRecordFoundException;
import cis.tinkoff.support.exceptions.InaccessibleActionException;
import cis.tinkoff.support.exceptions.RecordNotFoundException;
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

@Tag(name = "Resumes", description = "All actions with resumes.")
@Controller("/api/v1/resumes")
@Secured(SecurityRule.IS_AUTHENTICATED)
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;
    private final UserService userService;

    @Operation(method = "findAll", description = "Finds all resumes")
    @Get(value = "/all", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<Resume>> findAll() {
        return HttpResponse.ok(resumeService.getAll());
    }

    @Operation(method = "findUsersResumes", description = "Finds all user's resumes")
    @Get(value = "/users", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<Resume>> findUsersResumes(Authentication authentication)
            throws RecordNotFoundException, DeletedRecordFoundException {

        String email = authentication.getName();
        User resumeAuthor = userService.getByEmail(email);
        return HttpResponse.ok(resumeService.getALlByUser(resumeAuthor));
    }

    @Operation(method = "create", description = "Creates new resume")
    @Post(processes = MediaType.APPLICATION_JSON)
    public HttpResponse<Resume> create(@Body CreateResumeDTO dto,
                                       Authentication authentication) throws RecordNotFoundException, DeletedRecordFoundException {

        String email = authentication.getName();
        User author = userService.getByEmail(email);
        Resume resume = resumeService.create(author,
                dto.description(),
                dto.skills(),
                dto.direction());
        return HttpResponse.ok(resume);
    }

    @Operation(method = "softDelete", description = "Sets 'deleted' flag true")
    @Delete("/{id}")
    public void softDelete(@PathVariable Long id,
                           Authentication authentication) throws InaccessibleActionException {

        String email = authentication.getName();
        resumeService.softDelete(id, email);
    }
}
