package cis.tinkoff.repository;

import cis.tinkoff.model.Position;
import cis.tinkoff.model.Project;
import cis.tinkoff.model.User;
import io.micronaut.context.annotation.Parameter;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.Join;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.Sort;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.PageableRepository;
import io.micronaut.data.repository.jpa.JpaSpecificationExecutor;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

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
                       AND lower(position_.skills::text)::text[] @> coalesce(lower(:skills::text)::text[], lower(position_.skills::text)::text[])""",
            countQuery = """
                    SELECT count(position_.*) FROM position position_
                        JOIN project project_ ON position_.project_id = project_.id
                        JOIN dictionary_direction dd ON position_.direction = dd.direction_name
                        JOIN dictionary_project_status dps ON project_.status = dps.status_name
                             WHERE position_.is_visible = true
                               AND position_.user_id is null
                               AND position_.direction ilike coalesce(:direction, '%')
                               AND project_.status ilike coalesce(:status, '%')
                               AND lower(position_.skills::text)::text[] @> coalesce(lower(:skills::text)::text[], lower(position_.skills::text)::text[])""")
    Page<Position> searchAllVacancies(@Nullable String direction,
                                      @Nullable String status,
                                      @Nullable List<String> skills,
                                      Pageable pageable);

    @Override
    @Join(value = "project", type = Join.Type.FETCH)
    @Join(value = "project.leader", type = Join.Type.FETCH)
    @Join(value = "project.status", type = Join.Type.FETCH)
    @Join(value = "direction", type = Join.Type.FETCH)
    @Join(value = "user", type = Join.Type.LEFT_FETCH)
    Optional<Position> findById(@Id @NotNull Long id);

    @Join(value = "project", type = Join.Type.FETCH)
    @Join(value = "project.leader", type = Join.Type.FETCH)
    @Join(value = "project.status", type = Join.Type.FETCH)
    @Join(value = "direction", type = Join.Type.FETCH)
    @Join(value = "user", type = Join.Type.LEFT_FETCH)
    List<Position> findByIdInList(List<Long> ids,
                                  Sort sort);

    @Join(value = "project", type = Join.Type.FETCH)
    @Join(value = "project.leader", type = Join.Type.FETCH)
    @Join(value = "project.status", type = Join.Type.FETCH)
    @Join(value = "direction", type = Join.Type.FETCH)
    @Join(value = "user", type = Join.Type.FETCH)
    List<Position> findByUserIdAndProjectId(Long userId,
                                            Long projectId);

    @Join(value = "project", type = Join.Type.FETCH)
    @Join(value = "direction", type = Join.Type.FETCH)
    @Join(value = "user", type = Join.Type.FETCH)
    List<Position> findByProjectIdAndUserEmail(Long project_id,
                                               String userEmail);

    @Join(value = "project", type = Join.Type.FETCH)
    @Join(value = "project.leader", type = Join.Type.FETCH)
    @Join(value = "project.status", type = Join.Type.FETCH)
    @Join(value = "direction", type = Join.Type.FETCH)
    @Join(value = "user", type = Join.Type.LEFT_FETCH)
    List<Position> findByProjectIdAndIsVisibleAndIsDeletedFalseAndUserIsNull(Long project_id,
                                                                             Boolean isVisible);

    @Join(value = "project.leader", type = Join.Type.FETCH)
    @Join(value = "project.status", type = Join.Type.FETCH)
    @Join(value = "direction", type = Join.Type.FETCH)
    @Join(value = "user", type = Join.Type.FETCH)
    List<Position> retrieveByProjectId(Long project_id);

    @Query(value = """
            UPDATE position SET user_id = null, is_visible = false\040
            WHERE position.user_id = :userId\040
            AND position.project_id = :projectId
            """,
            nativeQuery = true)
    void deleteUserFromPositionsByUserIdAndProjectId(Long userId,
                                                     Long projectId);

    @Query(value = """
            UPDATE position SET user_id = null, is_visible = false\040
            WHERE position.user_id = :userId
            """,
            nativeQuery = true)
    void deleteUserFromAllPositionsByUserId(Long userId);

    @Query(value = """
            UPDATE position SET is_deleted = true, user_id = null, is_visible = false\040
            WHERE position.project_id = :projectId
            """,
            nativeQuery = true)
    void softDeletePositionsByProjectId(Long projectId);

    @Join(value = "direction", type = Join.Type.FETCH)
    @Join(value = "project", type = Join.Type.FETCH)
    Optional<Position> findByIdAndIsDeletedFalseAndIsVisibleTrue(@Id Long id);

    @Join(value = "direction", type = Join.Type.FETCH)
    @Join(value = "project.leader", type = Join.Type.FETCH)
    Optional<Position> getByIdAndIsDeletedFalseAndIsVisibleTrue(@Id Long id);

    @Query(
            nativeQuery = true,
            value = """
                    SELECT DISTINCT u.*
                    FROM position po
                        JOIN project p on po.project_id = p.id
                        JOIN position pos on p.id = pos.project_id
                        JOIN users u on u.id = pos.user_id
                    WHERE po.id = :id
                    """
    )
    List<User> findProjectMembersByPositionId(@Parameter Long id);

    @Query(
            nativeQuery = true,
            value = """
                    select u.email
                    from position po
                        join project p on po.project_id = p.id
                        join users u on u.id = p.leader_id
                    where po.id = :id;
                    """
    )
    String getProjectsLeadersEmailById(@Id Long id);

    @Join(value = "project.status", type = Join.Type.FETCH)
    @Join(value = "project.positions.user", type = Join.Type.FETCH)
    Project findProjectById(@Id Long id);

    @Join(value = "project", type = Join.Type.FETCH)
    @Join(value = "project.leader", type = Join.Type.FETCH)
    @Join(value = "project.status", type = Join.Type.FETCH)
    @Join(value = "direction", type = Join.Type.FETCH)
    @Join(value = "user", type = Join.Type.LEFT_FETCH)
    Optional<Position> findByIdAndIsDeletedFalse(@Id Long id);
}
