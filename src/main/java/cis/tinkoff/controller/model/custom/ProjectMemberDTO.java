package cis.tinkoff.controller.model.custom;

import cis.tinkoff.model.dictionary.DirectionDictionary;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude
public class ProjectMemberDTO {
    private Long userId;
    private String name;
    private String surname;
    private String pictureLink;
    private DirectionDictionary direction;
    private Long joinDate;
    private Boolean isLead;
}
