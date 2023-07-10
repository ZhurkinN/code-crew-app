package cis.tinkoff.repository;

import cis.tinkoff.model.Resume;
import cis.tinkoff.model.User;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.Join;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface ResumeRepository extends CrudRepository<Resume, Long> {

    @Join(value = "direction", type = Join.Type.FETCH)
    List<Resume> findByUserAndIsDeletedFalse(User user);

    @Join(value = "direction", type = Join.Type.FETCH)
    List<Resume> findByUserAndIsDeletedFalseAndIsActiveTrue(User user);

    void updateById(@Id Long id,
                    Boolean isDeleted);

    Boolean getIsActiveById(@Id Long id);

    void updateIsActiveById(@Id Long id,
                            Boolean isActive);

    User getUserById(Long id);
}
