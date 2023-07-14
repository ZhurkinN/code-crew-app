package cis.tinkoff.controller.model;

import cis.tinkoff.controller.model.generic.GenericDTO;
import cis.tinkoff.model.DirectionDictionary;
import cis.tinkoff.model.PositionRequest;
import cis.tinkoff.model.User;
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
public class ResumeDTO extends GenericDTO {

    private String description;
    private Boolean isActive;

    @JsonInclude
    private DirectionDictionary direction;

    @JsonInclude
    private List<String> skills;

    @JsonInclude
    private User user;
    private List<PositionRequest> requests;
}
