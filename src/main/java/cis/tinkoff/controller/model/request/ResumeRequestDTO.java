package cis.tinkoff.controller.model.request;

import cis.tinkoff.model.Position;
import cis.tinkoff.model.RequestStatusDictionary;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Accessors(chain = true)
public class ResumeRequestDTO {
    private String coverLetter;
    private Boolean isInvite;

    @JsonInclude
    private RequestStatusDictionary status;

    @JsonInclude
    private Position vacancy;
}
