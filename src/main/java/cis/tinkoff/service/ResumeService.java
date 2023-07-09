package cis.tinkoff.service;

import cis.tinkoff.model.Resume;
import cis.tinkoff.model.User;
import cis.tinkoff.support.exceptions.InaccessibleActionException;
import cis.tinkoff.support.exceptions.RecordNotFoundException;

import java.util.List;

public interface ResumeService {

    List<Resume> getAll();

    Resume getById(Long id) throws RecordNotFoundException;

    List<Resume> getALlByUser(String authorEmail) throws RecordNotFoundException;

    Resume create(User author,
                  String description,
                  List<String> skills,
                  String directionName) throws RecordNotFoundException;

    void softDelete(Long id,
                    String authorEmail) throws InaccessibleActionException;
}
