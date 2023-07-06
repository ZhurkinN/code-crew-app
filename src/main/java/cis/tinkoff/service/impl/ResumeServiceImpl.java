package cis.tinkoff.service.impl;

import cis.tinkoff.model.Resume;
import cis.tinkoff.repository.ResumeRepository;
import cis.tinkoff.service.ResumeService;
import io.micronaut.context.annotation.Primary;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Primary
@Singleton
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository resumeRepository;

    @Override
    public List<Resume> getAll() {
        return resumeRepository.list();
    }
}
