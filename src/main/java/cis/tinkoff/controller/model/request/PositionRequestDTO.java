package cis.tinkoff.controller.model.request;

import cis.tinkoff.controller.model.generic.GenericDTO;
import cis.tinkoff.model.RequestStatusDictionary;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class PositionRequestDTO extends GenericDTO {

    private String coverLetter;
    private Boolean isInvite;

    @JsonInclude
    private RequestStatusDictionary status;

    @JsonInclude
    private ResumeDTO resume;

}
