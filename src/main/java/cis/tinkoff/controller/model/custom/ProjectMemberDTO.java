package cis.tinkoff.controller.model.custom;

import cis.tinkoff.model.Position;
import cis.tinkoff.model.User;
import cis.tinkoff.model.dictionary.DirectionDictionary;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
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
