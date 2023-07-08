package cis.tinkoff.service;

import cis.tinkoff.model.DirectionDictionary;
import cis.tinkoff.model.ProjectStatusDictionary;
import cis.tinkoff.model.RequestStatusDictionary;
import cis.tinkoff.support.exceptions.RecordNotFoundException;

import java.util.List;

public interface DictionaryService {

    List<DirectionDictionary> getAllDirections();

    List<ProjectStatusDictionary> getAllProjectStatuses();

    List<RequestStatusDictionary> getAllRequestStatuses();

    List<DirectionDictionary> getAllAvailableDirections(String email) throws RecordNotFoundException;
}
