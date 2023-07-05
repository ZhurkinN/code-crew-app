package cis.tinkoff.controller;

import cis.tinkoff.model.Position;
import cis.tinkoff.model.Project;
import cis.tinkoff.service.PositionService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;

import java.util.List;

@Tag(name = "Positions/Vacancies", description = "All actions with positions of project including vacancies.")
@Controller("/positions")
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
            @QueryValue(value = "date", defaultValue = "0") Long date,
            @QueryValue(value = "status", defaultValue = "PREPARING") String status,
            @QueryValue(value = "direction") String direction,
            @QueryValue("skills") String skills
    ) {
        List<Position> vacancies = positionService.searchVacancyList(date, status, direction, skills);

        return HttpResponse.ok(vacancies);
    }
}
