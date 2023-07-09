package cis.tinkoff.controller.model;

import cis.tinkoff.model.ProjectContact;
import cis.tinkoff.model.ProjectStatusDictionary;
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
public class ProjectDTO {
    private Long id;
    private Boolean isLeader;
    private String title;
    private String theme;
    private String description;
    private Integer membersCount;
    private ProjectStatusDictionary status;
    private List<ProjectContact> contacts;
    private Integer vacanciesCount;
    private List<ProjectMemberDTO> members;
}
