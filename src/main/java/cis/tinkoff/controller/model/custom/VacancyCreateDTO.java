package cis.tinkoff.controller.model.custom;

import cis.tinkoff.model.enumerated.Direction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class VacancyCreateDTO {

    private Direction direction;
    private String description;
    private List<String> skills;
}
