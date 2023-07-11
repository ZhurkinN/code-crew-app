package cis.tinkoff.controller.model;

import cis.tinkoff.model.DirectionDictionary;
import cis.tinkoff.model.PositionRequest;
import cis.tinkoff.model.Resume;
import cis.tinkoff.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZoneOffset;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonInclude
public class SearchResumeDTO {
    private Long id;
    private DirectionDictionary direction;
    private String description;
    private List<String> skills;
    private Long createdWhen;
    private User user;

    public static SearchResumeDTO toDto(Resume resume) {
        if (resume == null) {
            return null;
        }

        SearchResumeDTO searchResumeDTO = SearchResumeDTO.builder()
                .id(resume.getId())
                .direction(resume.getDirection())
                .description(resume.getDescription())
                .skills(resume.getSkills())
                .createdWhen(resume.getCreatedWhen().toEpochSecond(ZoneOffset.UTC))
                .user(resume.getUser())
                .build();

        return searchResumeDTO;
    }

    public static List<SearchResumeDTO> toDtoList(List<Resume> resumes) {
        if (resumes == null) {
            return null;
        }

        List<SearchResumeDTO> searchResumeDTOList = resumes.stream()
                .map(SearchResumeDTO::toDto)
                .toList();

        return searchResumeDTOList;
    }
}
