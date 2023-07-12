package cis.tinkoff.service.impl;

import cis.tinkoff.controller.model.request.PositionRequestDTO;
import cis.tinkoff.controller.model.request.ResumeRequestDTO;
import cis.tinkoff.model.*;
import cis.tinkoff.model.enumerated.RequestStatus;
import cis.tinkoff.repository.*;
import cis.tinkoff.repository.dictionary.RequestStatusRepository;
import cis.tinkoff.service.PositionRequestService;
import cis.tinkoff.support.exceptions.InaccessibleActionException;
import cis.tinkoff.support.exceptions.RecordNotFoundException;
import cis.tinkoff.support.mapper.PositionRequestMapper;
import io.micronaut.context.annotation.Primary;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Primary
@Singleton
@RequiredArgsConstructor
public class PositionRequestServiceImpl implements PositionRequestService {

    private final UserRepository userRepository;
    private final PositionRequestRepository positionRequestRepository;
    private final ResumeRepository resumeRepository;
    private final PositionRepository positionRepository;
    private final RequestStatusRepository requestStatusRepository;
    private final ProjectRepository projectRepository;
    private final PositionRequestMapper positionRequestMapper;

    @Override
    public List<PositionRequest> getAll() {
        return positionRequestRepository.list();
    }

    @Override
    public PositionRequest sendRequestToVacancy(Long vacancyId, Long resumeId, String coverLetter, String email) throws RecordNotFoundException, InaccessibleActionException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RecordNotFoundException("User cannot be found"));
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new RecordNotFoundException("Resume cannot be found"));
        User resumeOwner = resumeRepository.getUserById(resumeId);

        Position position = positionRepository.findById(vacancyId)
                .orElseThrow(() -> new RecordNotFoundException("Position cannot be found"));
        Project project = positionRepository.getProjectById(position.getId());
        User projectLead = projectRepository.findLeaderById(project.getId());

        if (projectLead.getId().equals(user.getId())) {
            throw new InaccessibleActionException("Lead cannot send request to his project");
        }
        if (user.getId().equals(resumeOwner.getId())) {
            PositionRequest positionRequest = new PositionRequest();

            RequestStatus status = RequestStatus.IN_CONSIDERATION;
            RequestStatusDictionary requestStatusDictionary = requestStatusRepository.findById(status)
                    .orElseThrow(() -> new RecordNotFoundException("RequestStatus cannot be found"));
            positionRequest.setCoverLetter(coverLetter);
            positionRequest.setResume(resume);
            positionRequest.setIsInvite(false);
            positionRequest.setPosition(position);
            positionRequest.setStatus(requestStatusDictionary);

            positionRequestRepository.save(positionRequest);
            return positionRequest;
        } else {
            throw new InaccessibleActionException("User cannot send request with this resume");
        }
    }

    @Override
    public PositionRequest sendRequestToResume(Long vacancyId, Long resumeId, String coverLetter, String email) throws RecordNotFoundException, InaccessibleActionException {
        User projectLead = userRepository.findByEmail(email)
                .orElseThrow(() -> new RecordNotFoundException("User cannot be found"));
        Position position = positionRepository.findById(vacancyId)
                .orElseThrow(() -> new RecordNotFoundException("Position cannot be found"));
        Project project = positionRepository.getProjectById(position.getId());
        User realProjectLead = projectRepository.findLeaderById(project.getId());
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new RecordNotFoundException("Resume cannot be found"));
        User resumeOwner = resumeRepository.getUserById(resumeId);

        if (resumeOwner.getId().equals(projectLead.getId())) {
            throw new InaccessibleActionException("Lead cannot send request to himself");
        }

        if (projectLead.getId().equals(realProjectLead.getId())) {
            PositionRequest positionRequest = new PositionRequest();

            RequestStatus status = RequestStatus.IN_CONSIDERATION;
            RequestStatusDictionary requestStatusDictionary = requestStatusRepository.findById(status)
                    .orElseThrow(() -> new RecordNotFoundException("RequestStatus cannot be found"));

            positionRequest.setCoverLetter(coverLetter);
            positionRequest.setResume(resume);
            positionRequest.setIsInvite(true);
            positionRequest.setPosition(position);
            positionRequest.setStatus(requestStatusDictionary);

            positionRequestRepository.save(positionRequest);
            return positionRequest;
        } else {
            throw new InaccessibleActionException("User cannot send requests from this project");
        }
    }

    public List<PositionRequestDTO> getVacancyRequestsByVacancyId(Long id, String email) throws RecordNotFoundException, InaccessibleActionException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RecordNotFoundException("User cannot be found"));
        Project project = positionRepository.getProjectById(id);
        User projectLead = projectRepository.findLeaderById(project.getId());

        if (user.getId().equals(projectLead.getId())) {
            List<PositionRequest> requests = positionRequestRepository.findAllByPositionId(id);
            System.out.println(requests);
            return positionRequestMapper.toDtos(requests);
        } else {
            throw new InaccessibleActionException("User cannot check request to this vacancy");
        }
    }

    @Override
    public List<ResumeRequestDTO> getResumeRequestsByResumeId(Long resumeId, String email) throws RecordNotFoundException, InaccessibleActionException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RecordNotFoundException("User cannot be found"));
        User resumeOwner = resumeRepository.getUserById(resumeId);
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new RecordNotFoundException("Resume cannot be found"));;

        if (user.getId().equals(resumeOwner.getId())) {
            List<ResumeRequestDTO> dtos = new ArrayList<>();
//            List<PositionRequest> requests = resumeRepository.getRequestsById(resumeId);
            List<PositionRequest> requests = positionRequestRepository.findAllByResumeIdAndIsInviteTrue(resumeId);
            for (PositionRequest request : requests) {
                ResumeRequestDTO dto = new ResumeRequestDTO()
                        .setCoverLetter(request.getCoverLetter())
                        .setVacancy(request.getPosition())
                        .setIsInvite(request.getIsInvite())
                        .setStatus(request.getStatus());
                dtos.add(dto);
            }
            return dtos;
        } else {
            throw new InaccessibleActionException("User cannot see requests to this resume");
        }
    }

}
