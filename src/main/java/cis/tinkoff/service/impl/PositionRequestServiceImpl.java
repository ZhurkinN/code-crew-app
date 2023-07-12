package cis.tinkoff.service.impl;

import cis.tinkoff.controller.model.PositionRequestDTO;
import cis.tinkoff.controller.model.request.ResumeRequestDTO;
import cis.tinkoff.model.*;
import cis.tinkoff.model.enumerated.RequestStatus;
import cis.tinkoff.repository.PositionRepository;
import cis.tinkoff.repository.PositionRequestRepository;
import cis.tinkoff.repository.ResumeRepository;
import cis.tinkoff.repository.dictionary.RequestStatusRepository;
import cis.tinkoff.service.PositionRequestService;
import cis.tinkoff.support.exceptions.InaccessibleActionException;
import cis.tinkoff.support.exceptions.RecordNotFoundException;
import io.micronaut.context.annotation.Primary;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static cis.tinkoff.support.exceptions.constants.ErrorDisplayMessageKeeper.*;

@Primary
@Singleton
@RequiredArgsConstructor
public class PositionRequestServiceImpl implements PositionRequestService {

    private final PositionRequestRepository positionRequestRepository;
    private final ResumeRepository resumeRepository;
    private final PositionRepository positionRepository;
    private final RequestStatusRepository requestStatusRepository;

    @Override
    public List<PositionRequest> getAll() {
        return positionRequestRepository.list();
    }

    @Override
    public PositionRequest createPositionRequest(String authorEmail,
                                                 Long positionId,
                                                 Long resumeId,
                                                 String coverLetter) throws RecordNotFoundException, InaccessibleActionException {

        Resume resume = resumeRepository.findByIdAndIsDeletedFalseAndIsActiveTrue(resumeId)
                .orElseThrow(() -> new RecordNotFoundException(RESUME_NOT_FOUND));
        Position position = positionRepository.findByIdAndIsDeletedFalseAndIsVisibleTrue(positionId)
                .orElseThrow(() -> new RecordNotFoundException(POSITION_NOT_FOUND));
        RequestStatusDictionary defaultStatus = requestStatusRepository.findById(RequestStatus.IN_CONSIDERATION)
                .orElseThrow();

        validateUsersResumeOwnership(authorEmail, resumeId);
        validateUsersProjectMembership(authorEmail, positionId);
        validateUnansweredRequestDuplication(resume, positionId, defaultStatus);

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
                                                String coverLetter) throws RecordNotFoundException, InaccessibleActionException {

        Resume resume = resumeRepository.findByIdAndIsDeletedFalseAndIsActiveTrue(resumeId)
                .orElseThrow(() -> new RecordNotFoundException(RESUME_NOT_FOUND));
        Position position = positionRepository.findByIdAndIsDeletedFalseAndIsVisibleTrue(positionId)
                .orElseThrow(() -> new RecordNotFoundException(POSITION_NOT_FOUND));
        RequestStatusDictionary defaultStatus = requestStatusRepository.findById(RequestStatus.IN_CONSIDERATION)
                .orElseThrow();

        validateUsersResumeLeadership(authorEmail, positionId);
        validateUnansweredRequestDuplication(resume, positionId, defaultStatus);

        PositionRequest positionRequest = new PositionRequest()
                .setPosition(position)
                .setResume(resume)
                .setCoverLetter(coverLetter)
                .setIsInvite(true)
                .setStatus(defaultStatus);

        return positionRequestRepository.save(positionRequest);
    }

    @Override
    public List<PositionRequestDTO> getVacancyRequestsByVacancyId(Long id, String email) throws RecordNotFoundException, InaccessibleActionException {
        return null;
    }

    @Override
    public List<ResumeRequestDTO> getResumeRequestsByResumeId(Long resumeId, String email) throws RecordNotFoundException, InaccessibleActionException {
        return null;
    }

    private void validateUsersResumeOwnership(String userEmail,
                                              Long resumeId) {

        User author = resumeRepository.getUserById(resumeId);
        if (!userEmail.equals(author.getEmail())) {
            throw new InaccessibleActionException(RESUME_WRONG_ACCESS);
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
            throw new InaccessibleActionException(USER_ALREADY_IN_PROJECT);
        }
    }

    private void validateUnansweredRequestDuplication(Resume resume,
                                                      Long positionId,
                                                      RequestStatusDictionary defaultStatus) {

        List<PositionRequest> unansweredResumesRequests =
                positionRequestRepository.findByResumeAndIsDeletedFalseAndStatus(resume, defaultStatus);
        List<Long> requestsPositionIds = unansweredResumesRequests
                .stream()
                .map(e -> positionRequestRepository.findPositionById(e.getId()).getId())
                .toList();
        if (requestsPositionIds.contains(positionId)) {
            throw new InaccessibleActionException(SAME_REQUEST_ALREADY_EXISTS);
        }
    }

    private void validateUsersResumeLeadership(String authorEmail,
                                               Long positionId) {

    }
}
