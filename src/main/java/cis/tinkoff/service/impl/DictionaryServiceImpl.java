package cis.tinkoff.service.impl;

import cis.tinkoff.model.DirectionDictionary;
import cis.tinkoff.model.ProjectStatusDictionary;
import cis.tinkoff.model.RequestStatusDictionary;
import cis.tinkoff.repository.dictionary.DirectionRepository;
import cis.tinkoff.repository.dictionary.ProjectStatusRepository;
import cis.tinkoff.repository.dictionary.RequestStatusRepository;
import cis.tinkoff.service.DictionaryService;
import io.micronaut.context.annotation.Primary;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Primary
@Singleton
@RequiredArgsConstructor
public class DictionaryServiceImpl implements DictionaryService {

    private final DirectionRepository directionRepository;
    private final ProjectStatusRepository projectStatusRepository;
    private final RequestStatusRepository requestStatusRepository;

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
}
