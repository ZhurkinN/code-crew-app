package cis.tinkoff.repository;

import cis.tinkoff.model.Resume;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface ResumeRepository extends CrudRepository<Resume, Long> {
}
