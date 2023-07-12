package cis.tinkoff.controller.model;

import cis.tinkoff.controller.model.generic.GenericDTO;
import cis.tinkoff.model.Position;
import cis.tinkoff.model.RequestStatusDictionary;
import cis.tinkoff.model.Resume;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class PositionRequestDTO extends GenericDTO {

    private String coverLetter;
    private Boolean isInvite;

    @JsonInclude
    private RequestStatusDictionary status;

    @JsonInclude
    private Resume resume;

    @JsonInclude
    private Position position;
}
