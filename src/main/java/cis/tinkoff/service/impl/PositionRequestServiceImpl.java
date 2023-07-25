package cis.tinkoff.service.impl;

import cis.tinkoff.model.*;
import cis.tinkoff.model.dictionary.RequestStatusDictionary;
import cis.tinkoff.model.enumerated.NotificationType;
import cis.tinkoff.model.enumerated.RequestStatus;
import cis.tinkoff.repository.PositionRepository;
import cis.tinkoff.repository.PositionRequestRepository;
import cis.tinkoff.repository.ResumeRepository;
import cis.tinkoff.service.DictionaryService;
import cis.tinkoff.service.NotificationService;
import cis.tinkoff.service.PositionRequestService;
import cis.tinkoff.service.enumerated.RequestType;
import cis.tinkoff.support.exceptions.InaccessibleActionException;
import cis.tinkoff.support.exceptions.RecordNotFoundException;
import cis.tinkoff.support.exceptions.RequestAlreadyExistsException;
import cis.tinkoff.support.exceptions.RequestAlreadyProcessedException;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

import static cis.tinkoff.support.exceptions.constants.ErrorDisplayMessageKeeper.*;

@Singleton
@RequiredArgsConstructor
public class PositionRequestServiceImpl implements PositionRequestService {

    private final NotificationService notificationService;
    private final DictionaryService dictionaryService;
    private final PositionRequestRepository positionRequestRepository;
    private final ResumeRepository resumeRepository;
    private final PositionRepository positionRepository;

    @Override
    public PositionRequest createPositionRequest(String authorEmail,
                                                 Long positionId,
                                                 Long resumeId,
                                                 String coverLetter) {

        Resume resume = resumeRepository.findByIdAndIsDeletedFalseAndIsActiveTrue(resumeId)
                .orElseThrow(() -> new RecordNotFoundException(RESUME_NOT_FOUND, resumeId));
        Position position = positionRepository.getByIdAndIsDeletedFalseAndIsVisibleTrue(positionId)
                .orElseThrow(() -> new RecordNotFoundException(POSITION_NOT_FOUND, positionId));
        RequestStatusDictionary defaultStatus = dictionaryService.getRequestStatusDictionaryById(RequestStatus.IN_CONSIDERATION);

        validateUsersResumeOwnership(authorEmail, resumeId);
        validateUsersProjectMembership(authorEmail, positionId);
        validateUnansweredRequestDuplication(resume, positionId, defaultStatus, authorEmail);

        PositionRequest positionRequest = new PositionRequest()
                .setPosition(position)
                .setResume(resume)
                .setCoverLetter(coverLetter)
                .setIsInvite(false)
                .setStatus(defaultStatus);

        positionRequest = positionRequestRepository.save(positionRequest);

        notificationService.createNotification(
                position.getProject().getLeader().getId(),
                positionRequest.getId(),
                NotificationType.REQUEST
        );

        return positionRequest;
    }

    @Override
    public PositionRequest createPositionInvite(String authorEmail,
                                                Long positionId,
                                                Long resumeId,
                                                String coverLetter) {

        Resume resume = resumeRepository.findByIdAndIsDeletedFalseAndIsActiveTrue(resumeId)
                .orElseThrow(() -> new RecordNotFoundException(RESUME_NOT_FOUND, resumeId));
        Position position = positionRepository.findByIdAndIsDeletedFalseAndIsVisibleTrue(positionId)
                .orElseThrow(() -> new RecordNotFoundException(POSITION_NOT_FOUND, positionId));
        RequestStatusDictionary defaultStatus = dictionaryService.getRequestStatusDictionaryById(RequestStatus.IN_CONSIDERATION);

        validateUsersProjectLeadership(authorEmail, positionId);
        validateInvitedUsersProjectMembership(resumeId, positionId);
        validateUnansweredRequestDuplication(resume, positionId, defaultStatus, authorEmail);

        PositionRequest positionRequest = new PositionRequest()
                .setPosition(position)
                .setResume(resume)
                .setCoverLetter(coverLetter)
                .setIsInvite(true)
                .setStatus(defaultStatus);

        positionRequest = positionRequestRepository.save(positionRequest);

        notificationService.createNotification(
                resume.getUser().getId(),
                positionRequest.getId(),
                NotificationType.INVITE
        );

        return positionRequest;
    }

    @Override
    public List<PositionRequest> getPositionsRequests(Long positionId,
                                                      RequestType requestType,
                                                      String leaderEmail) {

        Position position = positionRepository.findByIdAndIsDeletedFalseAndIsVisibleTrue(positionId)
                .orElseThrow(() -> new RecordNotFoundException(POSITION_NOT_FOUND, positionId));
        validateUsersProjectLeadership(leaderEmail, positionId);
        RequestStatusDictionary inConsiderationStatus
                = dictionaryService.getRequestStatusDictionaryById(RequestStatus.IN_CONSIDERATION);

        List<PositionRequest> positionRequests =
                switch (requestType) {
                    case INCOMING -> positionRequestRepository.findAllByPositionAndStatusAndIsDeletedFalseAndIsInvite(
                            position,
                            inConsiderationStatus,
                            false
                    );
                    case SENT -> positionRequestRepository.findAllByPositionAndStatusAndIsDeletedFalseAndIsInvite(
                            position,
                            inConsiderationStatus,
                            true
                    );
                    case RECENT -> positionRequestRepository.findAllByPositionAndStatusNotEqualsAndIsDeletedFalse(
                            position,
                            inConsiderationStatus
                    );
                };
        positionRequests.forEach(e -> {
            User user = resumeRepository.getUserById(Objects.requireNonNull(e.getResume()).getId());
            User detailedUser = new User()
                    .setName(user.getName())
                    .setSurname(user.getSurname());

            detailedUser.setId(user.getId());
            detailedUser.setCreatedWhen(null);
            detailedUser.setIsDeleted(null);
            e.getResume().setUser(detailedUser);
        });

        return positionRequests;
    }

    @Override
    public List<PositionRequest> getResumesPositionRequests(Long resumeId,
                                                            RequestType requestType,
                                                            String resumeOwnerEmail) {

        Resume resume = resumeRepository.findByIdAndIsDeletedFalseAndIsActiveTrue(resumeId)
                .orElseThrow(() -> new RecordNotFoundException(RESUME_NOT_FOUND, resumeId));
        validateUsersResumeOwnership(resumeOwnerEmail, resumeId);
        RequestStatusDictionary inConsiderationStatus
                = dictionaryService.getRequestStatusDictionaryById(RequestStatus.IN_CONSIDERATION);

        List<PositionRequest> resumesPositionRequests =
                switch (requestType) {
                    case INCOMING -> positionRequestRepository.findAllByResumeAndStatusAndIsDeletedFalseAndIsInvite(
                            resume,
                            inConsiderationStatus,
                            true
                    );
                    case SENT -> positionRequestRepository.findAllByResumeAndStatusAndIsDeletedFalseAndIsInvite(
                            resume,
                            inConsiderationStatus,
                            false
                    );
                    case RECENT -> positionRequestRepository.findAllByResumeAndStatusNotEqualsAndIsDeletedFalse(
                            resume,
                            inConsiderationStatus
                    );
                };

        resumesPositionRequests.forEach(e -> {
            Project project = positionRepository.findProjectById(Objects.requireNonNull(e.getPosition()).getId());
            Project detailedProject = new Project()
                    .setStatus(project.getStatus())
                    .setTitle(project.getTitle());

            detailedProject.setId(project.getId());
            detailedProject.setCreatedWhen(null);
            detailedProject.setIsDeleted(null);
            e.getPosition().setProject(detailedProject);
        });

        return resumesPositionRequests;
    }

    @Override
    @Transactional
    public void processRequest(Long requestId,
                               Boolean isAccepted,
                               String respondentEmail) {

        PositionRequest request = positionRequestRepository.findByIdAndIsDeletedFalse(requestId)
                .orElseThrow(() -> new RecordNotFoundException(REQUEST_NOT_FOUND, requestId));
        RequestStatusDictionary status = request.getStatus();
        Resume resume = request.getResume();
        Position position = request.getPosition();

        Long targetUserId;
        validateRequestStatus(status, position.getId(), respondentEmail);
        validateInvitedUsersProjectMembership(resume.getId(), position.getId());
        if (request.getIsInvite()) {
            validateUsersResumeOwnership(respondentEmail, resume.getId());
            targetUserId = position.getProject().getLeader().getId();
        } else {
            validateUsersProjectLeadership(respondentEmail, position.getId());
            targetUserId = resume.getUser().getId();
        }

        NotificationType notificationType;

        if (isAccepted) {

            position.setUser(resume.getUser())
                    .setIsVisible(false)
                    .setJoinDate(System.currentTimeMillis());
            request.getResume().setIsActive(false);
            status.setStatusName(RequestStatus.ACCEPTED);

            notificationType = request.getIsInvite()
                    ? NotificationType.INVITE_APPROVED
                    : NotificationType.REQUEST_APPROVED;

        } else {
            status.setStatusName(RequestStatus.DECLINED);

            notificationType = request.getIsInvite()
                    ? NotificationType.INVITE_DECLINED
                    : NotificationType.REQUEST_DECLINED;
        }

        positionRequestRepository.update(request);

        notificationService.createNotification(
                targetUserId,
                request.getId(),
                notificationType
        );
    }

    @Override
    public PositionRequest findPositionRequestById(Long positionRequestId) {

        return positionRequestRepository.findByIdAndIsDeletedFalse(positionRequestId)
                .orElseThrow(() -> new RecordNotFoundException(
                        REQUEST_NOT_FOUND,
                        positionRequestId
                ));
    }

    private void validateRequestStatus(RequestStatusDictionary status,
                                       Long positionId,
                                       String userEmail) {

        if (!Objects.equals(status.getStatusName(), RequestStatus.IN_CONSIDERATION)) {
            throw new RequestAlreadyProcessedException(
                    userEmail,
                    positionId
            );
        }
    }


    private void validateUsersResumeOwnership(String userEmail,
                                              Long resumeId) {

        User author = resumeRepository.getUserById(resumeId);
        if (!userEmail.equals(author.getEmail())) {
            throw new InaccessibleActionException(
                    INACCESSIBLE_RESUME_ACTION,
                    userEmail,
                    resumeId
            );
        }
    }

    private void validateUsersProjectMembership(String userEmail,
                                                Long positionId) {

        List<User> projectMembers = positionRepository.findProjectMembersByPositionId(positionId);
        List<String> emails = projectMembers
                .stream()
                .map(User::getEmail)
                .toList();
        if (emails.contains(userEmail)) {
            throw new InaccessibleActionException(
                    USER_ALREADY_IN_PROJECT,
                    userEmail,
                    positionId
            );
        }
    }

    private void validateUnansweredRequestDuplication(Resume resume,
                                                      Long positionId,
                                                      RequestStatusDictionary defaultStatus,
                                                      String userEmail) {

        List<PositionRequest> unansweredResumesRequests =
                positionRequestRepository.findByResumeAndIsDeletedFalseAndStatus(resume, defaultStatus);
        List<Long> requestsPositionIds = unansweredResumesRequests
                .stream()
                .map(e -> positionRequestRepository.findPositionById(e.getId()).getId())
                .toList();
        if (requestsPositionIds.contains(positionId)) {
            throw new RequestAlreadyExistsException(userEmail);
        }
    }

    private void validateUsersProjectLeadership(String authorEmail,
                                                Long positionId) {
        String leaderEmail = positionRepository.getProjectsLeadersEmailById(positionId);
        if (!leaderEmail.equals(authorEmail)) {
            throw new InaccessibleActionException(
                    INACCESSIBLE_POSITION_ACTION,
                    authorEmail,
                    positionId
            );
        }
    }

    private void validateInvitedUsersProjectMembership(Long resumeId,
                                                       Long positionId) {
        User invitedUser = resumeRepository.getUserById(resumeId);
        validateUsersProjectMembership(invitedUser.getEmail(), positionId);
    }
}
