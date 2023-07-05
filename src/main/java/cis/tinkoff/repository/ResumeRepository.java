package cis.tinkoff.repository;

import cis.tinkoff.model.Resume;
import io.micronaut.data.annotation.Join;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface ResumeRepository extends CrudRepository<Resume, Long> {

    @Join(value = "user", type = Join.Type.FETCH)
    @Join(
            value = "direction",
            alias = "direction",
            type = Join.Type.FETCH
    )
    List<Resume> list();


}
