package cis.tinkoff.controller;

import cis.tinkoff.controller.model.request.PositionRequestDTO;
import cis.tinkoff.controller.model.request.ResumeRequestDTO;
import cis.tinkoff.controller.model.request.SendRequestDTO;
import cis.tinkoff.model.PositionRequest;
import cis.tinkoff.service.PositionRequestService;
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

@Tag(name = "Position requests", description = "All actions with requests for positions.")
@Controller("/api/v1/requests")
@Secured(SecurityRule.IS_AUTHENTICATED)
@RequiredArgsConstructor
public class PositionRequestController {

    private final PositionRequestService positionRequestService;

    @Operation(method = "findAll", description = "Finds all position' requests")
    @Get(produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<PositionRequest>> findAll() {
        return HttpResponse.ok(positionRequestService.getAll());
    }

    @Operation(method = "sendRequestToVacancy", description = "Send requests to vacancy")
    @Post(value = "/vacancies", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<SendRequestDTO> sendRequestToVacancy(Authentication authentication, @Body SendRequestDTO dto) throws RecordNotFoundException, InaccessibleActionException {
        String email = authentication.getName();

        positionRequestService.sendRequestToVacancy(
                dto.getVacancyId(),
                dto.getResumeId(),
                dto.getCoverLetter(),
                email);

        return HttpResponse.ok(dto);
    }

    @Operation(method = "sendRequestToResume", description = "Send requests to resume")
    @Post(value = "/resumes", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<SendRequestDTO> sendRequestToResume(Authentication authentication, @Body SendRequestDTO dto) throws RecordNotFoundException, InaccessibleActionException {
        String email = authentication.getName();

        positionRequestService.sendRequestToResume(
                dto.getVacancyId(),
                dto.getResumeId(),
                dto.getCoverLetter(),
                email);

        return HttpResponse.ok(dto);
    }

    @Operation(method = "getVacancyRequestsByVacancyId", description = "Get vacancy requests by vacancy id")
    @Get(value = "/vacancies/{id}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<PositionRequestDTO>> getVacancyRequestsByVacancyId(@PathVariable Long id,
                                                                                Authentication authentication) throws RecordNotFoundException, InaccessibleActionException {
        String email = authentication.getName();
        List<PositionRequestDTO> requests = positionRequestService.getVacancyRequestsByVacancyId(id, email);
        return HttpResponse.ok(requests);
    }

    // Доделать
    @Operation(method = "getResumeRequestsByResumeId", description = "Get resume requests by resume id")
    @Get(value = "/resumes/{id}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<ResumeRequestDTO>> getResumeRequestsByResumeId(@PathVariable Long id,
                                                                            Authentication authentication) throws RecordNotFoundException, InaccessibleActionException {
        String email = authentication.getName();
        List<ResumeRequestDTO> dtos = positionRequestService.getResumeRequestsByResumeId(id, email);
        return HttpResponse.ok(dtos);
    }

}
