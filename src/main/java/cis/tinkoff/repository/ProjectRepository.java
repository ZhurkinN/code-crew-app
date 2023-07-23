package cis.tinkoff.repository;

import cis.tinkoff.model.Project;
import cis.tinkoff.model.User;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.Join;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Where;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface ProjectRepository extends CrudRepository<Project, Long> {
    @Override
    @Where("is_deleted = false")
    @Join(value = "leader", type = Join.Type.FETCH)
    @Join(value = "status", type = Join.Type.FETCH)
    @Join(value = "status", type = Join.Type.FETCH)
    @Join(value = "contacts", type = Join.Type.LEFT_FETCH)
    @Join(value = "positions.direction", type = Join.Type.FETCH)
    @Join(value = "positions.user", type = Join.Type.LEFT_FETCH)
    Optional<Project> findById(@NotNull Long aLong);

    @Join(value = "leader", type = Join.Type.FETCH)
    @Join(value = "status", type = Join.Type.FETCH)
    List<Project> list();

    @Join(value = "positions.user", type = Join.Type.LEFT_FETCH)
    @Join(value = "status", type = Join.Type.FETCH)
    @Join(value = "leader", type = Join.Type.FETCH)
    List<Project> findByPositionsUserEmailAndIsDeletedFalse(String positionsUserEmail);

    @Join(value = "status", type = Join.Type.FETCH)
    List<Project> getByPositionsUserEmailAndIsDeletedFalse(String positionsUserEmail);

    @Join(value = "positions.user", type = Join.Type.LEFT_FETCH)
    @Join(value = "status", type = Join.Type.FETCH)
    @Join(value = "leader", type = Join.Type.FETCH)
    List<Project> findByLeaderEmailAndIsDeletedFalse(String leaderEmail);

    @Query(value = """
            UPDATE project SET is_deleted = true WHERE id = :id
            """,
            nativeQuery = true)
    void softDeleteProject(Long id);

    void updateLeaderByLeaderId(@Id Long id,
                                User leader);

    @Query(
            nativeQuery = true,
            value = """
                    INSERT INTO project_members (user_id, project_id)
                    VALUES (:userId, :projectId)
                    """
    )
    void saveMember(Long projectId,
                    Long userId);
}
