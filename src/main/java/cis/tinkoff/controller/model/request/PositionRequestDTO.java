package cis.tinkoff.controller.model.request;

import cis.tinkoff.controller.model.request.VacancyRequestDTO;
import cis.tinkoff.controller.model.generic.GenericDTO;
import cis.tinkoff.model.Position;
import cis.tinkoff.model.RequestStatusDictionary;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import cis.tinkoff.model.Resume;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class PositionRequestDTO extends GenericDTO {

    private String coverLetter;
    private Boolean isInvite;

    @JsonInclude
    private RequestStatusDictionary status;

    @JsonIgnore
    private Resume resume;

    @JsonInclude
    private VacancyRequestDTO vacancy;

}
