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

import java.util.List;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface ProjectRepository extends CrudRepository<Project, Long> {
    @Join(value = "positions.direction", type = Join.Type.FETCH)
    @Join(value = "status", type = Join.Type.FETCH)
    @Join(value = "leader", type = Join.Type.FETCH)
    @Join(value = "contacts", type = Join.Type.LEFT_FETCH)
    List<Project> findByIdInList(List<Long> id);

    @Join(value = "leader", type = Join.Type.FETCH)
    @Join(value = "status", type = Join.Type.FETCH)
    List<Project> list();

    User findLeaderById(Long id);

    @Query(value = """
            SELECT * FROM project p
            JOIN position ps on p.id = ps.project_id
            JOIN users u2 on p.leader_id = u2.id
            JOIN users u on ps.user_id = u.id
            WHERE u.email = :login
            OR u2.email = :login
            """,
            nativeQuery = true)
    @Join(value = "positions", type = Join.Type.FETCH)
    @Join(value = "status", type = Join.Type.FETCH)
    List<Project> findAllByUserEmail(String login);

    @Query(value = """
            SELECT * FROM project p
            JOIN position ps on p.id = ps.project_id
            JOIN users u on p.leader_id = u.id
            WHERE u.email = :login
            """,
            nativeQuery = true)
    @Join(value = "positions", type = Join.Type.FETCH)
    @Join(value = "status", type = Join.Type.FETCH)
    List<Project> findAllProjectsByLeadEmail(String login);

    @Where("@.is_deleted = false")
    @Join(value = "positions.user", type = Join.Type.LEFT_FETCH)
    @Join(value = "status", type = Join.Type.FETCH)
    List<Project> findByPositionsUserEmail(String positions_user_email);

    @Where("@.is_deleted = false")
    @Join(value = "positions.user", type = Join.Type.LEFT_FETCH)
    @Join(value = "status", type = Join.Type.FETCH)
    List<Project> findByLeaderEmail(String leader_email);

    @Query(value = """
            UPDATE project SET is_deleted = true WHERE id = :id
            """,
            nativeQuery = true)
    Project softDeleteProject(Long id);

    void updateLeaderByLeaderId(@Id Long id, User leader);
}
