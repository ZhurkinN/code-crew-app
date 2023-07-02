package cis.tinkoff.service.impl;

import cis.tinkoff.model.Resume;
import cis.tinkoff.repository.ResumeRepository;
import cis.tinkoff.service.ResumeService;
import io.micronaut.context.annotation.Primary;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;

@Primary
@Singleton
public class ResumeServiceImpl implements ResumeService {

    @Inject
    private ResumeRepository resumeRepository;

    @Override
    public List<Resume> getAll() {
        return resumeRepository.list();
    }
}
