package cis.tinkoff.service;

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

    List<PositionRequest> getPositionsRequests(Long positionId,
                                               String leaderEmail) throws RecordNotFoundException, InaccessibleActionException;

    List<PositionRequest> getResumesPositionRequests(Long resumeId,
                                                     String resumeOwnerEmail) throws RecordNotFoundException, InaccessibleActionException;

    void processRequest(Long requestId,
                        Boolean isAccepted,
                        String respondentEmail) throws RecordNotFoundException, InaccessibleActionException;
}
