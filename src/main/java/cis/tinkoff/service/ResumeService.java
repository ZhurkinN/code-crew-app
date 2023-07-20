package cis.tinkoff.service;

import cis.tinkoff.controller.model.custom.SearchDTO;
import cis.tinkoff.model.Resume;
import cis.tinkoff.model.enumerated.SortDirection;

import java.util.List;

public interface ResumeService {

    SearchDTO searchResumes(Integer page,
                            Integer sizeLimit,
                            SortDirection dateSort,
                            String direction,
                            String skills);

    Resume getById(Long id);

    List<Resume> getAllUsersResumes(String authorEmail);

    List<Resume> getAllActiveResumesByUser(String authorEmail);

    Resume create(String authorEmail,
                  String description,
                  List<String> skills,
                  String directionName);

    Resume update(String authorEmail,
                  Long resumeId,
                  String description,
                  List<String> skills,
                  String directionName);

    Resume updateActivity(Long id,
                          String authorEmail);

    void softDelete(Long id,
                    String authorEmail);
}
