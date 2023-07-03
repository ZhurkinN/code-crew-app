package cis.tinkoff.support.helper;

import cis.tinkoff.model.Position;
import io.micronaut.data.repository.jpa.criteria.QuerySpecification;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Locale;

public class Specifications {
    public static QuerySpecification<Position> searchVacancies(Long date, String status, String direction, String skills) {
        return (root, query, criteriaBuilder) -> {
            query.select(root.get("id"))
                    .where(
                            criteriaBuilder.lessThan(root.get("createdWhen"), new Timestamp(date))
                    )
//                    .where(
//                            criteriaBuilder.equal(root.get("direction"), direction)
//                    )
            ;
            return null;
        };
    }
}
