package cis.tinkoff.service.impl;

import cis.tinkoff.model.DirectionDictionary;
import cis.tinkoff.model.Resume;
import cis.tinkoff.model.User;
import cis.tinkoff.model.enumerated.Direction;
import cis.tinkoff.repository.ResumeRepository;
import cis.tinkoff.repository.UserRepository;
import cis.tinkoff.repository.dictionary.DirectionRepository;
import cis.tinkoff.service.ResumeService;
import cis.tinkoff.support.exceptions.DeletedRecordFoundException;
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
public class ResumeServiceImpl implements ResumeService {

    private final UserRepository userRepository;
    private final ResumeRepository resumeRepository;
    private final DirectionRepository directionRepository;

    @Override
    public List<Resume> getAll() {
        return (List<Resume>) resumeRepository.findAll();
    }

    @Override
    public Resume getById(Long id) throws RecordNotFoundException, DeletedRecordFoundException {

        Resume resume = resumeRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(RECORD_NOT_FOUND));
        if (!resume.getIsActive() || resume.getIsDeleted()) {
            throw new DeletedRecordFoundException(DELETED_OR_HIDDEN_RESUME_FOUND);
        }
        User author = resumeRepository.getUserById(id);
        resume.setUser(author);

        return resume;
    }

    @Override
    public List<Resume> getALlByUser(String authorEmail) throws RecordNotFoundException {

        User author = userRepository.findByEmail(authorEmail)
                .orElseThrow(() -> new RecordNotFoundException(RECORD_NOT_FOUND));
        List<Resume> resumes = resumeRepository.findByUserAndIsDeletedFalse(author);
        resumes.forEach(e -> e.setUser(author));
        return resumes;
    }

    @Override
    public List<Resume> getALlActiveByUser(String authorEmail) throws RecordNotFoundException {

        User author = userRepository.findByEmail(authorEmail)
                .orElseThrow(() -> new RecordNotFoundException(RECORD_NOT_FOUND));
        return resumeRepository.findByUserAndIsDeletedFalseAndIsActiveTrue(author);
    }

    @Override
    public Resume create(String authorEmail,
                         String description,
                         List<String> skills,
                         String directionName) throws RecordNotFoundException {

        User author = userRepository.findByEmail(authorEmail)
                .orElseThrow(() -> new RecordNotFoundException(RECORD_NOT_FOUND));
        Direction directionId = Direction.valueOf(directionName);
        DirectionDictionary direction = directionRepository.findById(directionId)
                .orElseThrow(() -> new RecordNotFoundException(RECORD_NOT_FOUND));
        Resume resume = new Resume()
                .setDescription(description)
                .setSkills(skills)
                .setUser(author)
                .setDirection(direction);

        return resumeRepository.save(resume);
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

        return resumeRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(RECORD_NOT_FOUND));
    }

    @Override
    public void softDelete(Long id, String authorEmail) throws InaccessibleActionException, RecordNotFoundException {

        User author = resumeRepository.getUserById(id);
        if (!authorEmail.equals(author.getEmail())) {
            throw new InaccessibleActionException(RESUME_WRONG_ACCESS);
        }

        resumeRepository.updateById(id, true);
    }

}
