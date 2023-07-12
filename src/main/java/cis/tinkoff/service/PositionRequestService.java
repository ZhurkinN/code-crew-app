package cis.tinkoff.service;

import cis.tinkoff.controller.model.PositionRequestDTO;
import cis.tinkoff.controller.model.request.ResumeRequestDTO;
import cis.tinkoff.model.PositionRequest;
import cis.tinkoff.support.exceptions.InaccessibleActionException;
import cis.tinkoff.support.exceptions.RecordNotFoundException;

import java.util.List;

public interface PositionRequestService {

    List<PositionRequest> getAll();

    PositionRequest createPositionRequest(String authorEmail,
                                          Long positionId,
                                          Long resumeId,
                                          String coverLetter) throws RecordNotFoundException, InaccessibleActionException;

    PositionRequest createPositionInvite(String authorEmail,
                                         Long positionId,
                                         Long resumeId,
                                         String coverLetter) throws RecordNotFoundException, InaccessibleActionException;

    List<PositionRequestDTO> getVacancyRequestsByVacancyId(Long id,
                                                           String email) throws RecordNotFoundException, InaccessibleActionException;

    List<ResumeRequestDTO> getResumeRequestsByResumeId(Long resumeId,
                                                       String email) throws RecordNotFoundException, InaccessibleActionException;
}
