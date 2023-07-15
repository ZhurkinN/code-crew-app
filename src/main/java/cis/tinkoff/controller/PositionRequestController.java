package cis.tinkoff.controller;

import cis.tinkoff.controller.model.PositionRequestDTO;
import cis.tinkoff.controller.model.ResumeInviteDTO;
import cis.tinkoff.controller.model.custom.CreateRequestDTO;
import cis.tinkoff.model.PositionRequest;
import cis.tinkoff.service.PositionRequestService;
import cis.tinkoff.support.exceptions.InaccessibleActionException;
import cis.tinkoff.support.exceptions.RecordNotFoundException;
import cis.tinkoff.support.mapper.PositionRequestMapper;
import cis.tinkoff.support.mapper.ResumeInviteMapper;
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
    private final PositionRequestMapper positionRequestMapper;
    private final ResumeInviteMapper resumeInviteMapper;

    @Operation(method = "findAll", description = "Finds all position' requests")
    @Get(value = "/all", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<PositionRequest>> findAll() {
        return HttpResponse.ok(positionRequestService.getAll());
    }

    @Operation(method = "createPositionRequest", description = "Creates request for joining position (for interested users)")
    @Post(value = "/vacancies", consumes = MediaType.APPLICATION_JSON)
    public HttpResponse<PositionRequestDTO> createPositionRequest(@Body CreateRequestDTO requestDto,
                                                                  Authentication authentication)
            throws RecordNotFoundException, InaccessibleActionException {

        String authorEmail = authentication.getName();
        PositionRequest positionRequest = positionRequestService.createPositionRequest(
                authorEmail,
                requestDto.positionId(),
                requestDto.resumeId(),
                requestDto.coverLetter()
        );
        PositionRequestDTO responseDto = positionRequestMapper.toDto(positionRequest);

        return HttpResponse.ok(responseDto);
    }

    @Operation(method = "createPositionInvite", description = "Creates invite to join position (for team leaders)")
    @Post(value = "/resumes", consumes = MediaType.APPLICATION_JSON)
    public HttpResponse<PositionRequestDTO> createPositionInvite(@Body CreateRequestDTO requestDto,
                                                                 Authentication authentication)
            throws RecordNotFoundException, InaccessibleActionException {

        String authorEmail = authentication.getName();
        PositionRequest positionRequest = positionRequestService.createPositionInvite(
                authorEmail,
                requestDto.positionId(),
                requestDto.resumeId(),
                requestDto.coverLetter()
        );
        PositionRequestDTO responseDto = positionRequestMapper.toDto(positionRequest);

        return HttpResponse.ok(responseDto);
    }

    @Operation(method = "getPositionRequests", description = "Get position requests by position id (for team leaders)")
    @Get(value = "/vacancies/{id}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<PositionRequestDTO>> getPositionRequests(@PathVariable Long id,
                                                                      Authentication authentication)
            throws RecordNotFoundException, InaccessibleActionException {

        String leaderEmail = authentication.getName();
        List<PositionRequest> positionRequests = positionRequestService.getPositionsRequests(id, leaderEmail);
        List<PositionRequestDTO> responseDtos = positionRequestMapper.toDtos(positionRequests);

        return HttpResponse.ok(responseDtos);
    }

    @Operation(method = "getResumeInvites", description = "Get position invites by resume id (for interested users)")
    @Get(value = "/resumes/{id}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<ResumeInviteDTO>> getResumeInvites(@PathVariable Long id,
                                                                Authentication authentication)
            throws RecordNotFoundException, InaccessibleActionException {

        String resumeOwnerEmail = authentication.getName();
        List<PositionRequest> resumeInvites = positionRequestService.getResumesPositionRequests(id, resumeOwnerEmail);
        List<ResumeInviteDTO> responseDtos = resumeInviteMapper.toDtos(resumeInvites);

        return HttpResponse.ok(responseDtos);
    }

}
