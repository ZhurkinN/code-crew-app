package cis.tinkoff.controller.model.custom;

import cis.tinkoff.model.enumerated.Direction;
import cis.tinkoff.model.enumerated.ProjectStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude
public class ProjectCreateDTO {

    private String title;
    private String theme;
    private String description;
    private Direction direction;
    private ProjectStatus status;
    private List<ContactDTO> contacts;
}
