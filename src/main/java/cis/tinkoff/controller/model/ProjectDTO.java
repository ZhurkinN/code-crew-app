package cis.tinkoff.controller.model;

import cis.tinkoff.controller.model.custom.ContactDTO;
import cis.tinkoff.controller.model.custom.ProjectMemberDTO;
import cis.tinkoff.model.dictionary.ProjectStatusDictionary;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@JsonInclude
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
