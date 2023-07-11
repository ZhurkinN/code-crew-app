package cis.tinkoff.controller;

import cis.tinkoff.controller.model.ResumeDTO;
import cis.tinkoff.controller.model.custom.InteractResumeDTO;
import cis.tinkoff.controller.model.custom.RequestsChoiceResumeDTO;
import cis.tinkoff.model.Resume;
import cis.tinkoff.service.ResumeService;
import cis.tinkoff.support.exceptions.DeletedRecordFoundException;
import cis.tinkoff.support.exceptions.InaccessibleActionException;
import cis.tinkoff.support.exceptions.RecordNotFoundException;
import cis.tinkoff.support.mapper.ResumeMapper;
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
    private final ResumeMapper resumeMapper;

    @Operation(method = "findAll", description = "Finds all resumes")
    @Get(value = "/all", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<ResumeDTO>> findAll() {

        List<Resume> resumes = resumeService.getAll();
        List<ResumeDTO> responseDtos = resumeMapper.toDtos(resumes);
        return HttpResponse.ok(responseDtos);
    }

    @Operation(method = "findById", description = "Finds resume by it's id")
    @Get(value = "/{id}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<ResumeDTO> findById(@PathVariable Long id)
            throws RecordNotFoundException, DeletedRecordFoundException {

        Resume resume = resumeService.getById(id);
        ResumeDTO responseDto = resumeMapper.toDto(resume);
        return HttpResponse.ok(responseDto);
    }

    @Operation(method = "findUsersResumes", description = "Finds all user's resumes")
    @Get(produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<ResumeDTO>> findUsersResumes(Authentication authentication)
            throws RecordNotFoundException {

        String authorEmail = authentication.getName();
        List<Resume> resumes = resumeService.getALlByUser(authorEmail);
        List<ResumeDTO> responseDtos = resumeMapper.toDtos(resumes);
        return HttpResponse.ok(responseDtos);
    }

    @Operation(method = "findUsersRequestsChoiceResumes", description = "Finds all active resume for choosing to create new request")
    @Get(value = "/active", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<RequestsChoiceResumeDTO>> findUsersRequestsChoiceResumes(Authentication authentication)
            throws RecordNotFoundException {

        String authorEmail = authentication.getName();
        List<Resume> resumes = resumeService.getALlActiveByUser(authorEmail);
        List<RequestsChoiceResumeDTO> responseDtos = resumes
                .stream()
                .map(e -> new RequestsChoiceResumeDTO(e.getId(), e.getDirection()))
                .toList();

        return HttpResponse.ok(responseDtos);
    }

    @Operation(method = "create", description = "Creates new resume")
    @Post(processes = MediaType.APPLICATION_JSON)
    public HttpResponse<ResumeDTO> create(@Body InteractResumeDTO requestDto,
                                          Authentication authentication) throws RecordNotFoundException, DeletedRecordFoundException {

        String authorEmail = authentication.getName();
        Resume resume = resumeService.create(
                authorEmail,
                requestDto.description(),
                requestDto.skills(),
                requestDto.direction()
        );
        ResumeDTO responseDto = resumeMapper.toDto(resume);
        return HttpResponse.ok(responseDto);
    }

    @Operation(method = "update", description = "Updates information about resume")
    @Patch(value = "/{id}", processes = MediaType.APPLICATION_JSON)
    public HttpResponse<ResumeDTO> update(@PathVariable Long id,
                                          @Body InteractResumeDTO requestDto,
                                          Authentication authentication)
            throws RecordNotFoundException, InaccessibleActionException {

        String authorEmail = authentication.getName();
        Resume updatedResume = resumeService.update(
                authorEmail,
                id,
                requestDto.description(),
                requestDto.skills(),
                requestDto.direction()
        );
        ResumeDTO responseDto = resumeMapper.toDto(updatedResume);
        return HttpResponse.ok(responseDto);
    }

    @Operation(method = "changeVisibility", description = "Changes resume's activity")
    @Post(value = "/active/{id}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<ResumeDTO> changeActivity(@PathVariable Long id,
                                                  Authentication authentication)
            throws InaccessibleActionException, RecordNotFoundException {

        String email = authentication.getName();
        Resume updatedResume = resumeService.updateActivity(id, email);
        ResumeDTO responseDto = resumeMapper.toDto(updatedResume);
        return HttpResponse.ok(responseDto);
    }

    @Operation(method = "softDelete", description = "Sets 'deleted' flag true")
    @Delete("/{id}")
    public void softDelete(@PathVariable Long id,
                           Authentication authentication)
            throws InaccessibleActionException, RecordNotFoundException {

        String email = authentication.getName();
        resumeService.softDelete(id, email);
    }
}
