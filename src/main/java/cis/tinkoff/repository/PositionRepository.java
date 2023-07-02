package cis.tinkoff.repository;

import cis.tinkoff.model.Position;
import io.micronaut.data.annotation.Where;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import io.micronaut.data.repository.jpa.criteria.QuerySpecification;

import java.util.List;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface PositionRepository extends CrudRepository<Position, Long> {
    Page<Position> findAll(QuerySpecification<Position> spec, Pageable pageable);
}
