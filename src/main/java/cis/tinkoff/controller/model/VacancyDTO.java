package cis.tinkoff.controller.model;

import cis.tinkoff.model.DirectionDictionary;
import cis.tinkoff.model.ProjectStatusDictionary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class VacancyDTO {
    private Long id;
    private LocalDateTime createdWhen;
    private DirectionDictionary direction;
    private List<String> skills;
    private ProjectStatusDictionary projectStatus;

}
