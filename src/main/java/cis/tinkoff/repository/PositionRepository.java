package cis.tinkoff.repository;

import cis.tinkoff.model.Position;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.Join;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.PageableRepository;
import io.micronaut.data.repository.jpa.JpaSpecificationExecutor;

import java.util.Collection;
import java.util.List;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface PositionRepository extends PageableRepository<Position, Long>, JpaSpecificationExecutor<Position> {
    @Query(value = """
            SELECT distinct position_.* FROM position position_
                JOIN project project_ ON position_.project_id = project_.id
                JOIN dictionary_direction dd ON position_.direction = dd.direction_name
                JOIN dictionary_project_status dps ON project_.status = dps.status_name
                     WHERE position_.is_visible = true
                       AND position_.user_id is null
                       AND position_.direction ilike coalesce(:direction, '%')
                       AND project_.status ilike coalesce(:status, '%')
                       AND position_.skills @> coalesce(:skills, position_.skills)""",
            countQuery = """
                    SELECT count(position_.*) FROM position position_
                        JOIN project project_ ON position_.project_id = project_.id
                        JOIN dictionary_direction dd ON position_.direction = dd.direction_name
                        JOIN dictionary_project_status dps ON project_.status = dps.status_name
                             WHERE position_.is_visible = true
                               AND position_.user_id is null
                               AND position_.direction ilike coalesce(:direction, '%')
                               AND project_.status ilike coalesce(:status, '%')
                               AND position_.skills @> coalesce(:skills, position_.skills)""")
    Page<Position> searchAllVacancies(@Nullable String direction, @Nullable String status, @Nullable List<String> skills, Pageable pageable);

    @Join(value = "project", type = Join.Type.FETCH)
    @Join(value = "project.status", type = Join.Type.FETCH)
    @Join(value = "direction", type = Join.Type.FETCH)
    @Join(value = "user", type = Join.Type.FETCH)
    Iterable<Position> findByIdInList(Collection<Long> id);

    @Join(value = "project", type = Join.Type.FETCH)
    @Join(value = "project.status", type = Join.Type.FETCH)
    @Join(value = "direction", type = Join.Type.FETCH)
    @Join(value = "user", type = Join.Type.FETCH)
    List<Position> findByUserIdAndProjectId(Long user_id, Long project_id);
}
