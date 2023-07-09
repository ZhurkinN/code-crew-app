package cis.tinkoff.controller.model;

import cis.tinkoff.controller.model.generic.GenericDTO;
import cis.tinkoff.model.Position;
import cis.tinkoff.model.Project;
import cis.tinkoff.model.Resume;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class UserDTO extends GenericDTO {

    private String email;
    private String name;
    private String surname;
    private String mainInformation;
    private String pictureLink;
    private List<Position> positions;

    @JsonInclude
    private List<String> contacts;

    @JsonInclude
    private List<Resume> resumes;

    @JsonInclude
    private List<Project> leadProjects;

    @JsonInclude
    private List<Project> projects;

}
