package cis.tinkoff.service;

import cis.tinkoff.model.Resume;
import cis.tinkoff.support.exceptions.DeletedRecordFoundException;
import cis.tinkoff.support.exceptions.InaccessibleActionException;
import cis.tinkoff.support.exceptions.RecordNotFoundException;

import java.util.List;

public interface ResumeService {

    List<Resume> getAll();

    Resume getById(Long id) throws RecordNotFoundException, DeletedRecordFoundException;

    List<Resume> getALlByUser(String authorEmail) throws RecordNotFoundException;

    List<Resume> getALlActiveByUser(String authorEmail) throws RecordNotFoundException;

    Resume create(String authorEmail,
                  String description,
                  List<String> skills,
                  String directionName) throws RecordNotFoundException;

    Resume updateActivity(Long id,
                          String authorEmail) throws InaccessibleActionException, RecordNotFoundException;

    void softDelete(Long id,
                    String authorEmail) throws InaccessibleActionException, RecordNotFoundException;
}
