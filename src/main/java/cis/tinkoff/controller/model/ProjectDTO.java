package cis.tinkoff.controller.model;

import cis.tinkoff.controller.model.custom.ContactDTO;
import cis.tinkoff.controller.model.custom.ProjectMemberDTO;
import cis.tinkoff.model.dictionary.ProjectStatusDictionary;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@JsonInclude
@NoArgsConstructor
public class ProjectDTO {

    private Long id;
    private Boolean isLeader;
    private String title;
    private String theme;
    private String description;
    private Integer membersCount;
    private ProjectStatusDictionary status;
    private List<ContactDTO> contacts;
    private Integer vacanciesCount;
    private List<ProjectMemberDTO> members;
    private Long createdWhen;
}

