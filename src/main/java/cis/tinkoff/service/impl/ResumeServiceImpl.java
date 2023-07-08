package cis.tinkoff.service.impl;

import cis.tinkoff.model.DirectionDictionary;
import cis.tinkoff.model.Resume;
import cis.tinkoff.model.User;
import cis.tinkoff.model.enumerated.Direction;
import cis.tinkoff.repository.ResumeRepository;
import cis.tinkoff.repository.dictionary.DirectionRepository;
import cis.tinkoff.service.ResumeService;
import cis.tinkoff.support.exceptions.InaccessibleActionException;
import cis.tinkoff.support.exceptions.RecordNotFoundException;
import io.micronaut.context.annotation.Primary;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static cis.tinkoff.support.exceptions.constants.ErrorDisplayMessageKeeper.RECORD_NOT_FOUND;
import static cis.tinkoff.support.exceptions.constants.ErrorDisplayMessageKeeper.RESUME_WRONG_ACCESS;

@Primary
@Singleton
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository resumeRepository;
    private final DirectionRepository directionRepository;

    @Override
    public List<Resume> getAll() {
        return resumeRepository.list();
    }

    @Override
    public List<Resume> getALlByUser(User resumeAuthor) {

        return resumeRepository.findByUser(resumeAuthor);
    }

    @Override
    public Resume create(User author,
                         String description,
                         List<String> skills,
                         String directionName) throws RecordNotFoundException {

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
    public void softDelete(Long id, String authorEmail) throws InaccessibleActionException {

        User author = resumeRepository.getUserById(id);
        if (!authorEmail.equals(author.getEmail())) {
            throw new InaccessibleActionException(RESUME_WRONG_ACCESS);
        }

        resumeRepository.updateById(id, true);
    }
}
