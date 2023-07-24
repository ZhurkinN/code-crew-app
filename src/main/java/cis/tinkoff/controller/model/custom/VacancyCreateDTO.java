package cis.tinkoff.controller.model.custom;

import cis.tinkoff.model.enumerated.Direction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class VacancyCreateDTO {
    private Direction direction;
    private String description;
    private List<String> skills;
}
