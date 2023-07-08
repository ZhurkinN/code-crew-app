package cis.tinkoff.controller;

import cis.tinkoff.controller.model.VacancyDTO;
import cis.tinkoff.model.Position;
import cis.tinkoff.model.enumerated.SortDirection;
import cis.tinkoff.service.PositionService;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;

import java.util.List;

@Tag(name = "Positions/Vacancies", description = "All actions with positions of project including vacancies.")
@Controller("/api/v1/positions")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class PositionController {

    @Inject
    private PositionService positionService;

    @Operation(method = "findAll", description = "Finds all positions")
    @Get(produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<Position>> findAll() {
        return HttpResponse.ok(positionService.getAll());
    }

    @Operation(method = "findAll", description = "Finds all vacancies by searched parameters")
    @Get(uri = "/vacancies", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<?> searchVacancies(
            @QueryValue(value = "page", defaultValue = "0") Integer page,
            @QueryValue(value = "size", defaultValue = "1") Integer sizeLimit,
            @Nullable @QueryValue(value = "dateSort", defaultValue = "null") SortDirection dateSort,
            @Nullable @QueryValue(value = "status", defaultValue = "PREPARING") String status,
            @Nullable @QueryValue(value = "direction") String direction,
            @Nullable @QueryValue("skills") String skills
    ) {
        List<VacancyDTO> vacancies = positionService.searchVacancyList(
                page,
                sizeLimit,
                dateSort,
                status,
                direction,
                skills
        );

        return HttpResponse.ok(vacancies);
    }
}
