package cis.tinkoff.support.mapper;

import cis.tinkoff.controller.model.request.PositionRequestDTO;
import cis.tinkoff.controller.model.request.ResumeDTO;
import cis.tinkoff.controller.model.request.UserRequestDTO;
import cis.tinkoff.model.DirectionDictionary;
import cis.tinkoff.model.PositionRequest;
import cis.tinkoff.model.Resume;
import cis.tinkoff.model.User;
import cis.tinkoff.repository.ResumeRepository;
import cis.tinkoff.support.mapper.generic.GenericMapper;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.time.ZoneOffset;
import java.util.Objects;

@Singleton
@RequiredArgsConstructor
public class PositionRequestMapper extends GenericMapper<PositionRequest, PositionRequestDTO> {
    private final ResumeRepository resumeRepository;
    @Override
    public PositionRequestDTO toDto(PositionRequest model) {
        if (Objects.isNull(model)) {
            return null;
        }

        Resume resume = model.getResume();
        User user = resumeRepository.getUserById(resume.getId());
        DirectionDictionary direction = resumeRepository.getDirectionById(resume.getId());
        ResumeDTO resumeDTO = new ResumeDTO()
                .setId(resume.getId())
                .setCreatedWhen(resume.getCreatedWhen().toEpochSecond(ZoneOffset.UTC))
                .setUser(new UserRequestDTO(user.getName(), user.getSurname()))
                .setDescription(resume.getDescription())
                .setDirection(direction)
                .setSkills(resume.getSkills());

        PositionRequestDTO dto = new PositionRequestDTO()
                .setResume(resumeDTO)
                .setCoverLetter(model.getCoverLetter())
                .setStatus(model.getStatus())
                .setIsInvite(model.getIsInvite());
        setGenericFields(model, dto);
        return dto;
    }
}
