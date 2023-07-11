package cis.tinkoff.controller.model.request;

import cis.tinkoff.model.DirectionDictionary;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Accessors(chain = true)
public class ResumeDTO {

    protected Long id;
    private Long createdWhen;
    private String description;
    private List<String> skills;

    @JsonInclude
    private DirectionDictionary direction;

    @JsonInclude
    private UserRequestDTO user;

}