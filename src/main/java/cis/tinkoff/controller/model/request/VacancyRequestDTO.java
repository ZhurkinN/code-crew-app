package cis.tinkoff.controller.model.request;

import cis.tinkoff.controller.model.generic.GenericDTO;
import cis.tinkoff.model.DirectionDictionary;
import cis.tinkoff.model.Project;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Accessors(chain = true)
public class VacancyRequestDTO extends GenericDTO {
    private String description;
    private List<String> skills;

    @JsonInclude
    private DirectionDictionary direction;

    @JsonInclude
    private Project project;

}