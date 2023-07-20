package cis.tinkoff.controller.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class ResumeInviteDTO extends PositionRequestDTO {

    private Integer membersCount;
}
