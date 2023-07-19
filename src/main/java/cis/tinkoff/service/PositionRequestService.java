package cis.tinkoff.service;

import cis.tinkoff.model.PositionRequest;

import java.util.List;

public interface PositionRequestService {

    List<PositionRequest> getAll();

    PositionRequest createPositionRequest(String authorEmail,
                                          Long positionId,
                                          Long resumeId,
                                          String coverLetter);

    PositionRequest createPositionInvite(String authorEmail,
                                         Long positionId,
                                         Long resumeId,
                                         String coverLetter);

    List<PositionRequest> getPositionsRequests(Long positionId,
                                               String leaderEmail);

    List<PositionRequest> getResumesPositionRequests(Long resumeId,
                                                     String resumeOwnerEmail);

    void processRequest(Long requestId,
                        Boolean isAccepted,
                        String respondentEmail);
}
