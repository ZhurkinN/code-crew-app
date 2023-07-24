package cis.tinkoff.controller.model;

import cis.tinkoff.controller.model.generic.GenericDTO;
import cis.tinkoff.model.DirectionDictionary;
import cis.tinkoff.model.PositionRequest;
import cis.tinkoff.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Accessors(chain = true) // вот это можно вынести отовсюду в ломбок.конфиг
@NoArgsConstructor
public class ResumeDTO extends GenericDTO {

    private String description;
    private Boolean isActive;

    @JsonInclude
    private DirectionDictionary direction;

    @JsonInclude
    private List<String> skills;

    @JsonInclude
    private User user;
    private List<PositionRequest> requests;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResumeDTO resumeDTO)) return false;
        return Objects.equals(getDescription(), resumeDTO.getDescription()) && Objects.equals(getIsActive(), resumeDTO.getIsActive()) && Objects.equals(getDirection(), resumeDTO.getDirection()) && Objects.equals(getSkills(), resumeDTO.getSkills()) && Objects.equals(getUser(), resumeDTO.getUser()) && Objects.equals(getRequests(), resumeDTO.getRequests());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDescription(), getIsActive(), getDirection(), getSkills(), getUser(), getRequests());
    }
}
