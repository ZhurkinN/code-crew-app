package cis.tinkoff.service;

import cis.tinkoff.model.Project;

public interface ProjectSupportService {
    boolean isUserProjectLeader(String login, Long projectId);

    Project getProjectByIdOrElseThrow(Long id);
}
