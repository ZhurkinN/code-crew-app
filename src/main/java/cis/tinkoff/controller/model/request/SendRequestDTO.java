package cis.tinkoff.controller.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SendRequestDTO {

    private Long resumeId;
    private Long vacancyId;
    private String coverLetter;

}
