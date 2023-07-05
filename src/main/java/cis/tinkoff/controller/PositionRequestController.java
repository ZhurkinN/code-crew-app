package cis.tinkoff.controller;

import cis.tinkoff.model.PositionRequest;
import cis.tinkoff.service.PositionRequestService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;

import java.util.List;

@Tag(name = "Position requests", description = "All actions with requests for positions.")
@Controller("/requests")
public class PositionRequestController {

    @Inject
    private PositionRequestService positionRequestService;

    @Operation(method = "findAll", description = "Finds all position' requests")
    @Get(produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<PositionRequest>> findAll() {
        return HttpResponse.ok(positionRequestService.getAll());
    }
}