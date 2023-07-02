package cis.tinkoff.repository;

import cis.tinkoff.model.Position;
import cis.tinkoff.model.Project;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import io.micronaut.data.repository.jpa.criteria.QuerySpecification;

import java.util.List;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface ProjectRepository extends CrudRepository<Project, Long> {
    List<Project> findAll(QuerySpecification<Position> spec, Pageable pageable);
}
