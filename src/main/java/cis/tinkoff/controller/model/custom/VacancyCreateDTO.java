package cis.tinkoff.controller.model.custom;

import cis.tinkoff.model.enumerated.Direction;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class VacancyCreateDTO {
    private Direction direction;
    private String description;
    private List<String> skills;
}
