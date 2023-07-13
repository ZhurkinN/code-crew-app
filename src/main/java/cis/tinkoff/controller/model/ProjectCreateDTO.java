package cis.tinkoff.controller.model;

import cis.tinkoff.model.enumerated.Direction;
import cis.tinkoff.model.enumerated.ProjectStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonInclude
public class ProjectCreateDTO {
    private String title;
    private String theme;
    private String description;
    private Direction direction;
    private ProjectStatus status;
    private List<ContactDTO> contacts;
}
