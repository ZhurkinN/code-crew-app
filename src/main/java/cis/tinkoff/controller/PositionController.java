package cis.tinkoff.controller;

import cis.tinkoff.controller.model.VacancyDTO;
import cis.tinkoff.controller.model.custom.ProjectMemberDTO;
import cis.tinkoff.controller.model.custom.SearchDTO;
import cis.tinkoff.controller.model.custom.VacancyCreateDTO;
import cis.tinkoff.service.PositionService;
import cis.tinkoff.service.enumerated.SortDirection;
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
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Tag(name = "Positions/Vacancies", description = "All actions with positions of project including vacancies.")
@Controller("/api/v1/positions")
@Secured(SecurityRule.IS_AUTHENTICATED)
@RequiredArgsConstructor
public class PositionController {

    private final PositionService positionService;

    @Operation(method = "searchVacancies", description = "Finds all vacancies by searched parameters")
    @Get(uri = "/search", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<SearchDTO> searchVacancies(
            @QueryValue(value = "page", defaultValue = "0") Integer page,
            @QueryValue(value = "size", defaultValue = "1") Integer sizeLimit,
            @Nullable @QueryValue(value = "dateSort") SortDirection dateSort,
            @Nullable @QueryValue(value = "status") String status,
            @Nullable @QueryValue(value = "direction") String direction,
            @Nullable @QueryValue("skills") String skills
    ) {
        SearchDTO searchDTO = positionService.searchVacancyList(
                page,
                sizeLimit,
                dateSort,
                status,
                direction,
                skills
        );

        return HttpResponse.ok(searchDTO);
    }

    @Operation(method = "getVacancyById", description = "Get vacancy by id")
    @Get(uri = "/{id}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<VacancyDTO> getVacancyById(
            @PathVariable Long id,
            Authentication authentication
    ) {
        String userEmail = authentication.getName();
        VacancyDTO vacancyDTO = positionService.getVacancyById(id, userEmail);

        return HttpResponse.ok(vacancyDTO);
    }

    @Operation(method = "getProjectVacancies", description = "Get vacancies of the project by project id")
    @Get(uri = "/projects", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<VacancyDTO>> getProjectVacancies(
            Authentication authentication,
            @QueryValue Long projectId,
            @QueryValue Boolean isVisible
    ) {
        List<VacancyDTO> vacancyDTOList = positionService.getProjectVacancies(
                authentication.getName(),
                projectId,
                isVisible
        );

        return HttpResponse.ok(vacancyDTOList);
    }

    @Operation(method = "getProjectMembers", description = "Get members of the project by project id")
    @Get(uri = "/projects/members", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<ProjectMemberDTO>> getProjectMembers(
            Authentication authentication,
            @QueryValue Long projectId
    ) {
        List<ProjectMemberDTO> projectMembers = positionService.getProjectMembers(authentication.getName(), projectId);

        return HttpResponse.ok(projectMembers);
    }

    @Operation(method = "createVacancy", description = "Create vacancy")
    @Post(produces = MediaType.APPLICATION_JSON)
    public HttpResponse<VacancyDTO> createVacancy(
            Authentication authentication,
            @QueryValue Long projectId,
            @Body VacancyCreateDTO vacancyCreateDTO
    ) {
        VacancyDTO vacancyDTO = positionService.createVacancy(authentication.getName(), projectId, vacancyCreateDTO);

        return HttpResponse.ok(vacancyDTO);
    }

    @Operation(method = "updateVacancy", description = "Update vacancy")
    @Patch(uri = "/{id}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<VacancyDTO> updateVacancy(
            Authentication authentication,
            @PathVariable Long id,
            @Body VacancyCreateDTO updateVacancyDTO
    ) {
        VacancyDTO vacancyDTO = positionService.updateVacancy(id, authentication.getName(), updateVacancyDTO);

        return HttpResponse.ok(vacancyDTO);
    }

    @Operation(method = "changeVisibility", description = "Change vacancy visibility")
    @Post(uri = "/visible/{id}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<VacancyDTO> changeVisibility(
            Authentication authentication,
            @PathVariable Long id
    ) {
        VacancyDTO vacancyDTO = positionService.changeVisibility(id, authentication.getName());

        return HttpResponse.ok(vacancyDTO);
    }

    @Operation(method = "deleteVacancy", description = "Soft delete vacancy")
    @Delete(uri = "/{id}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<?> deleteVacancy(
            Authentication authentication,
            @PathVariable Long id
    ) {
        positionService.deleteVacancy(id, authentication.getName());

        return HttpResponse.ok();
    }
}
