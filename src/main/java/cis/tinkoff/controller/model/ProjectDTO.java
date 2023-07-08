package cis.tinkoff.controller.model;

import cis.tinkoff.model.ProjectStatusDictionary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProjectDTO {
    private Long id;
    private Boolean isLeader;
    private String title;
    private String theme;
    private String description;
    private Integer membersCount;
    private ProjectStatusDictionary status;
}
