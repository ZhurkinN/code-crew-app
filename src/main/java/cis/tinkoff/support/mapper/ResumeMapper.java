package cis.tinkoff.support.mapper;

import cis.tinkoff.controller.model.ResumeDTO;
import cis.tinkoff.model.Resume;
import cis.tinkoff.support.mapper.generic.GenericMapper;
import jakarta.inject.Singleton;

@Singleton
public class ResumeMapper extends GenericMapper<Resume, ResumeDTO> {

    @Override
    public ResumeDTO toDto(Resume model) {
        return null;
    }
}
