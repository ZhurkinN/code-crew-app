package cis.tinkoff.service.impl;

import cis.tinkoff.model.*;
import cis.tinkoff.model.enumerated.RequestStatus;
import cis.tinkoff.model.enumerated.RequestType;
import cis.tinkoff.repository.*;
import cis.tinkoff.repository.dictionary.RequestStatusRepository;
import cis.tinkoff.service.PositionRequestService;
import cis.tinkoff.support.exceptions.InaccessibleActionException;
import cis.tinkoff.support.exceptions.RecordNotFoundException;
import cis.tinkoff.support.exceptions.RequestAlreadyExistsException;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

import static cis.tinkoff.support.exceptions.constants.ErrorDisplayMessageKeeper.*;

@Singleton
@RequiredArgsConstructor
public class PositionRequestServiceImpl implements PositionRequestService {

    private final PositionRequestRepository positionRequestRepository;
    private final ResumeRepository resumeRepository;
    private final PositionRepository positionRepository;
    private final RequestStatusRepository requestStatusRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Override
    public PositionRequest createPositionRequest(String authorEmail,
                                                 Long positionId,
                                                 Long resumeId,
                                                 String coverLetter) {

        Resume resume = resumeRepository.findByIdAndIsDeletedFalseAndIsActiveTrue(resumeId)
                .orElseThrow(() -> new RecordNotFoundException(RESUME_NOT_FOUND, resumeId));
        Position position = positionRepository.findByIdAndIsDeletedFalseAndIsVisibleTrue(positionId)
                .orElseThrow(() -> new RecordNotFoundException(POSITION_NOT_FOUND, positionId));
        RequestStatusDictionary defaultStatus = requestStatusRepository.findById(RequestStatus.IN_CONSIDERATION)
                .orElseThrow();

        validateUsersResumeOwnership(authorEmail, resumeId);
        validateUsersProjectMembership(authorEmail, positionId);
        validateUnansweredRequestDuplication(resume, positionId, defaultStatus, authorEmail);

        PositionRequest positionRequest = new PositionRequest()
                .setPosition(position)
                .setResume(resume)
                .setCoverLetter(coverLetter)
                .setIsInvite(false)
                .setStatus(defaultStatus);

        return positionRequestRepository.save(positionRequest);
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
        RequestStatusDictionary defaultStatus = requestStatusRepository.findById(RequestStatus.IN_CONSIDERATION)
                .orElseThrow();

        validateUsersProjectLeadership(authorEmail, positionId);
        validateInvitedUsersProjectMembership(resumeId, positionId);
        validateUnansweredRequestDuplication(resume, positionId, defaultStatus, authorEmail);

        PositionRequest positionRequest = new PositionRequest()
                .setPosition(position)
                .setResume(resume)
                .setCoverLetter(coverLetter)
                .setIsInvite(true)
                .setStatus(defaultStatus);

        return positionRequestRepository.save(positionRequest);
    }

    @Override
    public List<PositionRequest> getPositionsRequests(Long positionId,
                                                      RequestType requestType,
                                                      String leaderEmail) {

        Position position = positionRepository.findByIdAndIsDeletedFalseAndIsVisibleTrue(positionId)
                .orElseThrow(() -> new RecordNotFoundException(POSITION_NOT_FOUND, positionId));
        validateUsersProjectLeadership(leaderEmail, positionId);
        RequestStatusDictionary inConsiderationStatus = requestStatusRepository
                .findById(RequestStatus.IN_CONSIDERATION)
                .orElseThrow();

        List<PositionRequest> positionRequests = switch (requestType) {
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
        RequestStatusDictionary inConsiderationStatus = requestStatusRepository
                .findById(RequestStatus.IN_CONSIDERATION)
                .orElseThrow();

        List<PositionRequest> resumesPositionRequests = switch (requestType) {
            case INCOMING -> positionRequestRepository.findAllByResumeAndStatusAndIsDeletedFalseAndIsInvite(
                    resume,
                    inConsiderationStatus,
                    false
            );
            case SENT -> positionRequestRepository.findAllByResumeAndStatusAndIsDeletedFalseAndIsInvite(
                    resume,
                    inConsiderationStatus,
                    true
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
                    .setMembers(project.getMembers());

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

        validateInvitedUsersProjectMembership(resume.getId(), position.getId());
        if (request.getIsInvite()) {
            validateUsersResumeOwnership(respondentEmail, resume.getId());
        } else {
            validateUsersProjectLeadership(respondentEmail, position.getId());
        }

        if (isAccepted) {

            status.setStatusName(RequestStatus.ACCEPTED);
            projectRepository.saveMember(position.getProject().getId(), resume.getUser().getId());
            resumeRepository.updateIsActiveById(resume.getId(), false);
            positionRepository.updateIsVisibleAndJoinDateAndUserById(
                    position.getId(),
                    false,
                    System.currentTimeMillis(),
                    resume.getUser()
            );
        } else {

            status.setStatusName(RequestStatus.DECLINED);
        }

        positionRequestRepository.update(request);
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
