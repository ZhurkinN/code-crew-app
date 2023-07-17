package cis.tinkoff.service.impl;

import cis.tinkoff.controller.model.custom.SearchDTO;
import cis.tinkoff.model.DirectionDictionary;
import cis.tinkoff.model.Resume;
import cis.tinkoff.model.User;
import cis.tinkoff.model.enumerated.Direction;
import cis.tinkoff.model.enumerated.SortDirection;
import cis.tinkoff.model.generic.GenericModel;
import cis.tinkoff.repository.ResumeRepository;
import cis.tinkoff.repository.UserRepository;
import cis.tinkoff.repository.dictionary.DirectionRepository;
import cis.tinkoff.service.ResumeService;
import cis.tinkoff.support.exceptions.DeletedRecordFoundException;
import cis.tinkoff.support.exceptions.InaccessibleActionException;
import cis.tinkoff.support.exceptions.RecordNotFoundException;
import cis.tinkoff.support.mapper.ResumeMapper;
import io.micronaut.context.annotation.Primary;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.Sort;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static cis.tinkoff.support.exceptions.constants.ErrorDisplayMessageKeeper.*;

@Primary
@Singleton
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {

    private final UserRepository userRepository;
    private final ResumeRepository resumeRepository;
    private final DirectionRepository directionRepository;
    private final ResumeMapper resumeMapper;

    @Override
    public List<Resume> getAll() {
        return (List<Resume>) resumeRepository.findAll();
    }

    @Override
    public Resume getById(Long id) throws RecordNotFoundException, DeletedRecordFoundException {

        Resume resume = resumeRepository.findByIdAndIsDeletedFalseAndIsActiveTrue(id)
                .orElseThrow(() -> new RecordNotFoundException(RESUME_NOT_FOUND));
        resume.getUser().setPassword(null);

        return resume;
    }

    @Override
    public List<Resume> getALlByUser(String authorEmail) throws RecordNotFoundException {

        User author = userRepository.findByEmailAndIsDeletedFalse(authorEmail)
                .orElseThrow(() -> new RecordNotFoundException(USER_NOT_FOUND));
        List<Resume> resumes = resumeRepository.findByUserAndIsDeletedFalse(author);
        author.setPassword(null);
        resumes.forEach(e -> e.setUser(author));

        return resumes;
    }

    @Override
    public List<Resume> getALlActiveByUser(String authorEmail) throws RecordNotFoundException {

        User author = userRepository.findByEmailAndIsDeletedFalse(authorEmail)
                .orElseThrow(() -> new RecordNotFoundException(USER_NOT_FOUND));
        return resumeRepository.findByUserAndIsDeletedFalseAndIsActiveTrue(author);
    }

    @Override
    public Resume create(String authorEmail,
                         String description,
                         List<String> skills,
                         String directionName) throws RecordNotFoundException {

        User author = userRepository.findByEmailAndIsDeletedFalse(authorEmail)
                .orElseThrow(() -> new RecordNotFoundException(USER_NOT_FOUND));
        Direction directionId = Direction.valueOf(directionName);
        DirectionDictionary direction = directionRepository.findById(directionId)
                .orElseThrow(() -> new RecordNotFoundException(DIRECTION_NOT_FOUND));

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
                         String directionName) throws RecordNotFoundException, InaccessibleActionException {

        Resume resume = resumeRepository.findByIdAndIsDeletedFalse(resumeId)
                .orElseThrow(() -> new RecordNotFoundException(RESUME_NOT_FOUND));
        User author = resumeRepository.getUserById(resumeId);
        Direction directionId = Direction.valueOf(directionName);
        DirectionDictionary direction = directionRepository.findById(directionId)
                .orElseThrow(() -> new RecordNotFoundException(DIRECTION_NOT_FOUND));
        if (!authorEmail.equals(author.getEmail())) {
            throw new InaccessibleActionException(RESUME_WRONG_ACCESS);
        }
        resume.setDescription(description)
                .setSkills(skills)
                .setDirection(direction)
                .setUser(author);

        return resumeRepository.update(resume);
    }

    @Override
    public Resume updateActivity(Long id,
                                 String authorEmail) throws InaccessibleActionException, RecordNotFoundException {

        User author = resumeRepository.getUserById(id);
        if (!authorEmail.equals(author.getEmail())) {
            throw new InaccessibleActionException(RESUME_WRONG_ACCESS);
        }
        boolean newActivity = !resumeRepository.getIsActiveById(id);
        resumeRepository.updateIsActiveById(id, newActivity);

        return resumeRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new RecordNotFoundException(RESUME_NOT_FOUND));
    }

    @Override
    public void softDelete(Long id,
                           String authorEmail) throws InaccessibleActionException {

        User author = resumeRepository.getUserById(id);
        if (!authorEmail.equals(author.getEmail())) {
            throw new InaccessibleActionException(RESUME_WRONG_ACCESS);
        }

        resumeRepository.updateById(id, true);
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
