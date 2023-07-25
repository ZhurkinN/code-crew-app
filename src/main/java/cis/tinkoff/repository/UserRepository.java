package cis.tinkoff.repository;

import cis.tinkoff.model.User;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.Join;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmailAndIsDeletedFalse(String email);

    boolean existsByEmail(String email);

    void update(@Id Long id,
                String[] contacts);

    void update(@Id Long id,
                Boolean isDeleted);

    Optional<User> findByIdAndIsDeletedFalse(@Id Long id);

    @Query(
            nativeQuery = true,
            value = """
                    UPDATE position SET user_id = null, is_visible = false
                    WHERE position.user_id = :userId
                    """
    )
    void deleteUserFromAllPositionsByUserId(Long userId);

    @Join(value = "resumes", type = Join.Type.FETCH)
    User getByEmail(String email);

}
