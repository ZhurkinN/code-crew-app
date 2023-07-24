package cis.tinkoff.controller;

import cis.tinkoff.model.dictionary.DirectionDictionary;
import cis.tinkoff.model.dictionary.ProjectStatusDictionary;
import cis.tinkoff.model.dictionary.RequestStatusDictionary;
import cis.tinkoff.service.DictionaryService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Tag(name = "Dictionaries", description = "All actions with dictionary-type models.")
@Controller("/api/v1/dictionaries")
@Secured(SecurityRule.IS_ANONYMOUS)
@RequiredArgsConstructor
public class DictionaryController {

    private final DictionaryService dictionaryService;

    @Operation(method = "getDirections", description = "Returns all vacancy' directions")
    @Get(value = "/directions", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<DirectionDictionary>> getDirections() {

        return HttpResponse.ok(dictionaryService.getAllDirections());
    }

    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Operation(method = "getUsersResumeAvailableDirections", description = "Returns all available directions for making resumes")
    @Get(value = "/directions/resumes", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<DirectionDictionary>> getUsersResumeAvailableDirections(Authentication authentication) {

        String email = authentication.getName();
        return HttpResponse.ok(dictionaryService.getAllAvailableDirections(email));
    }

    @Operation(method = "getProjectStatuses", description = "Returns all project statuses")
    @Get(value = "/project-statuses", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<ProjectStatusDictionary>> getProjectStatuses() {

        return HttpResponse.ok(dictionaryService.getAllProjectStatuses());
    }

    @Operation(method = "getRequestStatuses", description = "Returns all position request statuses")
    @Get(value = "/request-statuses", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<RequestStatusDictionary>> getRequestStatuses() {

        return HttpResponse.ok(dictionaryService.getAllRequestStatuses());
    }

}
