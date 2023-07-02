package cis.tinkoff.controller;

import cis.tinkoff.model.Resume;
import cis.tinkoff.service.ResumeService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;

import java.util.List;

@Tag(name = "Resumes", description = "All actions with resumes.")
@Controller("/resumes")
public class ResumeController {

    @Inject
    private ResumeService resumeService;

    @Operation(method = "findAll", description = "Finds all resumes")
    @Get(produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<Resume>> findAll() {
        return HttpResponse.ok(resumeService.getAll());
    }
}
