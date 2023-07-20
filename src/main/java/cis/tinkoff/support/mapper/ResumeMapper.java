package cis.tinkoff.support.mapper;

import cis.tinkoff.controller.model.ResumeDTO;
import cis.tinkoff.model.Resume;
import cis.tinkoff.support.mapper.generic.GenericMapper;
import jakarta.inject.Singleton;

import java.util.Objects;

@Singleton
public class ResumeMapper extends GenericMapper<Resume, ResumeDTO> {

    @Override
    public ResumeDTO toDto(Resume resume) {

        if (Objects.isNull(resume)) {
            return null;
        }

        ResumeDTO dto = new ResumeDTO()
                .setDescription(resume.getDescription())
                .setIsActive(resume.getIsActive())
                .setDirection(resume.getDirection())
                .setSkills(resume.getSkills())
                .setUser(resume.getUser())
                .setRequests(resume.getRequests());
        setGenericFields(resume, dto);
        return dto;
    }
}
