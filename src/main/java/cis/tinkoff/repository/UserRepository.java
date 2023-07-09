package cis.tinkoff.repository;

import cis.tinkoff.model.User;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    void updateByEmail(String email,
                       Boolean isDeleted);

    void update(@Id Long id,
                String[] contacts);

    void update(@Id Long id,
                Boolean isDeleted);

    List<User> findByIdInList(Collection<Long> id);
}
