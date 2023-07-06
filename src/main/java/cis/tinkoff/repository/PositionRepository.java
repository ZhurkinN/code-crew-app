package cis.tinkoff.repository;

import cis.tinkoff.model.Position;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.jdbc.annotation.JoinTable;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.PageableRepository;
import io.micronaut.data.repository.jpa.JpaSpecificationExecutor;

import java.util.List;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface PositionRepository extends PageableRepository<Position, Long>, JpaSpecificationExecutor<Position> {
    @JoinTable(name = "project")
    @Query(value = """
            SELECT * FROM position p
                JOIN project pj ON p.project_id = pj.id
                JOIN dictionary_direction dd ON p.direction = dd.direction_name
                JOIN dictionary_project_status dps ON pj.status = dps.status_name
                     WHERE p.is_visible = true
                       AND p.user_id is null
                       AND p.direction = :direction
                       AND pj.status = :status""")
    List<Position> searchAllVacancies(String direction, String status, List<String> skills);
}
