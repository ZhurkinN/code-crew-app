package cis.tinkoff.service.impl;

import cis.tinkoff.model.*;
import cis.tinkoff.repository.ResumeRepository;
import cis.tinkoff.repository.UserRepository;
import cis.tinkoff.repository.dictionary.DirectionRepository;
import cis.tinkoff.repository.dictionary.ProjectStatusRepository;
import cis.tinkoff.repository.dictionary.RequestStatusRepository;
import cis.tinkoff.service.DictionaryService;
import cis.tinkoff.support.exceptions.RecordNotFoundException;
import io.micronaut.context.annotation.Primary;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static cis.tinkoff.support.exceptions.constants.ErrorDisplayMessageKeeper.RECORD_NOT_FOUND;

@Primary
@Singleton
@RequiredArgsConstructor
public class DictionaryServiceImpl implements DictionaryService {

    private final DirectionRepository directionRepository;
    private final ProjectStatusRepository projectStatusRepository;
    private final RequestStatusRepository requestStatusRepository;
    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;

    @Override
    public List<DirectionDictionary> getAllDirections() {
        return (List<DirectionDictionary>) directionRepository.findAll();
    }

    @Override
    public List<ProjectStatusDictionary> getAllProjectStatuses() {
        return (List<ProjectStatusDictionary>) projectStatusRepository.findAll();
    }

    @Override
    public List<RequestStatusDictionary> getAllRequestStatuses() {
        return (List<RequestStatusDictionary>) requestStatusRepository.findAll();
    }

    @Override
    public List<DirectionDictionary> getAllAvailableDirections(String email) throws RecordNotFoundException {

        List<DirectionDictionary> allDirections = (List<DirectionDictionary>) directionRepository.findAll();
        User resumeAuthor = userRepository.findByEmail(email)
                .orElseThrow(() -> new RecordNotFoundException(RECORD_NOT_FOUND));
        List<Resume> userResumes = resumeRepository.findByUser(resumeAuthor);
        List<DirectionDictionary> usedDirections = new ArrayList<>();

        userResumes.forEach(e -> usedDirections.add(e.getDirection()));
        allDirections.removeAll(usedDirections);
        return allDirections;
    }
}