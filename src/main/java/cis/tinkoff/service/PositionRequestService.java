package cis.tinkoff.service;

import cis.tinkoff.model.PositionRequest;
import cis.tinkoff.service.enumerated.RequestType;

import java.util.List;

public interface PositionRequestService {

    PositionRequest createPositionRequest(String authorEmail,
                                          Long positionId,
                                          Long resumeId,
                                          String coverLetter);

    PositionRequest createPositionInvite(String authorEmail,
                                         Long positionId,
                                         Long resumeId,
                                         String coverLetter);

    List<PositionRequest> getPositionsRequests(Long positionId,
                                               RequestType requestType,
                                               String leaderEmail);

    List<PositionRequest> getResumesPositionRequests(Long resumeId,
                                                     RequestType requestType,
                                                     String resumeOwnerEmail);

    void processRequest(Long requestId,
                        Boolean isAccepted,
                        String respondentEmail);

    PositionRequest findPositionRequestById(Long positionRequestId);
}
