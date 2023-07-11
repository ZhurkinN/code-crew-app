package cis.tinkoff.controller.model.request;

import lombok.*;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Accessors(chain = true)
public class UserRequestDTO {

    private String name;
    private String surname;
}
