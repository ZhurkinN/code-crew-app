package cis.tinkoff.service.impl;

import cis.tinkoff.controller.model.custom.SearchDTO;
import cis.tinkoff.model.DirectionDictionary;
import cis.tinkoff.model.Resume;
import cis.tinkoff.model.User;
import cis.tinkoff.model.enumerated.Direction;
import cis.tinkoff.model.generic.GenericModel;
import cis.tinkoff.repository.ResumeRepository;
import cis.tinkoff.repository.UserRepository;
import cis.tinkoff.repository.dictionary.DirectionRepository;
import cis.tinkoff.service.ResumeService;
import cis.tinkoff.service.enumerated.SortDirection;
import cis.tinkoff.support.exceptions.InaccessibleActionException;
import cis.tinkoff.support.exceptions.RecordNotFoundException;
import cis.tinkoff.support.mapper.ResumeMapper;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.Sort;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static cis.tinkoff.support.exceptions.constants.ErrorDisplayMessageKeeper.*;

@Singleton
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {

    private final UserRepository userRepository;
    private final ResumeRepository resumeRepository;
    private final DirectionRepository directionRepository;
    private final ResumeMapper resumeMapper;

    @Override
    public Resume getById(Long resumeId,
                          String userEmail) {

        Resume resume = resumeRepository.findByIdAndIsDeletedFalse(resumeId)
                .orElseThrow(() -> new RecordNotFoundException(RESUME_NOT_FOUND, resumeId));
        resume.getUser().setPassword(null);

        if (!resume.getIsActive() && !resume.getUser().getEmail().equals(userEmail)) {
            throw new RecordNotFoundException(RESUME_NOT_FOUND, resumeId);
        }

        return resume;
    }

    @Override
    public List<Resume> getUsersResumes(String authorEmail,
                                        Boolean isActive) {

        User author = userRepository.findByEmailAndIsDeletedFalse(authorEmail)
                .orElseThrow(() -> new RecordNotFoundException(USER_NOT_FOUND_BY_EMAIL, authorEmail));
        List<Resume> resumes = resumeRepository.findByUserAndIsDeletedFalseAndIsActive(author, isActive);
        resumes.forEach(e -> e.getUser().setPassword(null));

        return resumes;
    }

    @Override
    public List<Resume> getAllActiveResumesByUser(String authorEmail) {

        User author = userRepository.findByEmailAndIsDeletedFalse(authorEmail)
                .orElseThrow(() -> new RecordNotFoundException(USER_NOT_FOUND_BY_EMAIL, authorEmail));
        return resumeRepository.findByUserAndIsDeletedFalseAndIsActiveTrue(author);
    }

    @Override
    public Resume create(String authorEmail,
                         String description,
                         List<String> skills,
                         String directionName) {

        User author = userRepository.findByEmailAndIsDeletedFalse(authorEmail)
                .orElseThrow(() -> new RecordNotFoundException(USER_NOT_FOUND_BY_EMAIL, authorEmail));
        Direction directionId = Direction.valueOf(directionName);
        DirectionDictionary direction = directionRepository.findById(directionId)
                .orElseThrow(() -> new RecordNotFoundException(DIRECTION_NOT_FOUND, String.valueOf(directionId)));

        Resume resume = new Resume()
                .setDescription(description)
                .setSkills(skills)
                .setUser(author)
                .setDirection(direction);

        return resumeRepository.save(resume);
    }

    @Override
    public Resume update(String authorEmail,
                         Long resumeId,
                         String description,
                         List<String> skills,
                         String directionName) {

        Resume resume = resumeRepository.findByIdAndIsDeletedFalse(resumeId)
                .orElseThrow(() -> new RecordNotFoundException(RESUME_NOT_FOUND, resumeId));
        Direction directionId = Direction.valueOf(directionName);
        DirectionDictionary direction = directionRepository.findById(directionId)
                .orElseThrow(() -> new RecordNotFoundException(DIRECTION_NOT_FOUND, String.valueOf(directionId)));

        if (!authorEmail.equals(resume.getUser().getEmail())) {
            throw new InaccessibleActionException(
                    INACCESSIBLE_RESUME_ACTION,
                    authorEmail,
                    resumeId
            );
        }

        resume.setDescription(description)
                .setSkills(skills)
                .setDirection(direction)
                .setUser(resume.getUser());

        return resumeRepository.update(resume);
    }

    @Override
    public Resume updateActivity(Long resumeId,
                                 String authorEmail) {

        User author = resumeRepository.getUserById(resumeId);
        if (!authorEmail.equals(author.getEmail())) {
            throw new InaccessibleActionException(
                    INACCESSIBLE_RESUME_ACTION,
                    authorEmail,
                    resumeId
            );
        }
        boolean newActivity = !resumeRepository.getIsActiveById(resumeId);
        resumeRepository.updateIsActiveById(resumeId, newActivity);

        return resumeRepository.getByIdAndIsDeletedFalse(resumeId)
                .orElseThrow(() -> new RecordNotFoundException(RESUME_NOT_FOUND, resumeId));
    }

    @Override
    public void softDelete(Long resumeId,
                           String authorEmail) {

        User author = resumeRepository.getUserById(resumeId);
        if (!authorEmail.equals(author.getEmail())) {
            throw new InaccessibleActionException(
                    INACCESSIBLE_RESUME_ACTION,
                    authorEmail,
                    resumeId
            );
        }

        resumeRepository.updateById(resumeId, true);
    }

    @Override
    public SearchDTO searchResumes(Integer page,
                                   Integer sizeLimit,
                                   SortDirection dateSort,
                                   String direction,
                                   String skills) {
        String resumeDirection = null;
        List<String> skillList = null;

        if (Objects.nonNull(direction)) {
            resumeDirection = Direction.valueOf(direction).toString();
        }
        if (Objects.nonNull(skills)) {
            skillList = Arrays.stream(skills.split(" ")).toList();
        }

        Sort.Order sortOrder = new Sort.Order("createdWhen");
        if (Objects.nonNull(dateSort)) {
            Sort.Order.Direction sortDirection = Sort.Order.Direction.valueOf(dateSort.name());
            sortOrder = new Sort.Order("createdWhen", sortDirection, false);
        }

        Pageable pageable = Pageable.from(page, sizeLimit, Sort.of(sortOrder));

        Page<Resume> resumePage = resumeRepository.searchAllResumes(
                resumeDirection,
                skillList,
                pageable
        );

        List<Resume> resumes = resumeRepository.findByIdInList(
                resumePage
                        .getContent()
                        .stream()
                        .map(GenericModel::getId)
                        .toList(),
                Sort.of(sortOrder)
        );

        return SearchDTO.toDto(
                resumeMapper.toDtos(resumes),
                resumePage.getTotalPages()
        );
    }

}
