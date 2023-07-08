package cis.tinkoff.support.helper;

import cis.tinkoff.model.DirectionDictionary;
import cis.tinkoff.model.Position;
import cis.tinkoff.model.Project;
import cis.tinkoff.model.ProjectStatusDictionary;
import cis.tinkoff.model.enumerated.Direction;
import io.micronaut.data.repository.jpa.criteria.QuerySpecification;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;

public class Specifications {
    public static QuerySpecification<Position> searchVacancies(String status, String direction, String skills) {
        return (root, query, criteriaBuilder) -> {
            Join<Position, Project> projectJoin = root.join("project", JoinType.INNER);
            Join<Project, ProjectStatusDictionary> projectProjectStatusDictionaryJoin = projectJoin.join("status");
            Join<Position, DirectionDictionary> directionDictionaryJoin = root.join("direction", JoinType.LEFT);

            query.where(
                    criteriaBuilder.and(
//                            criteriaBuilder.equal(projectProjectStatusDictionaryJoin.get("statusName"), ProjectStatus.valueOf(status)),
//                            criteriaBuilder.equal(projectJoin.get("title"), status)
                            criteriaBuilder.equal(directionDictionaryJoin.get("directionName"), Direction.valueOf(direction)),
                            criteriaBuilder.isTrue(root.get("isVisible")),
                            criteriaBuilder.isNull(root.get("user"))
                    )
            );

            return null;
        };
    }
}
