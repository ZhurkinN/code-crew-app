package cis.tinkoff.controller;

import cis.tinkoff.model.Resume;
import cis.tinkoff.service.ResumeService;
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

@Tag(name = "Resumes", description = "All actions with resumes.")
@Controller("/api/v1/resumes")
@Secured(SecurityRule.IS_AUTHENTICATED)
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    @Operation(method = "findAll", description = "Finds all resumes")
    @Get(produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<Resume>> findAll() {
        return HttpResponse.ok(resumeService.getAll());
    }
}
