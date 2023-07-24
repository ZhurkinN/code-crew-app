package cis.tinkoff.controller.model;

import cis.tinkoff.model.dictionary.DirectionDictionary;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class VacancyDTO {

    private Long id;
    private DirectionDictionary direction;
    private String description;
    private List<String> skills;
    private Long createdWhen;
    private Boolean isVisible;
    private ProjectDTO project;
}

