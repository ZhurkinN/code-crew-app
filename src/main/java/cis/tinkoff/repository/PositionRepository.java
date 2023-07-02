package cis.tinkoff.repository;

import cis.tinkoff.model.Position;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface PositionRepository extends CrudRepository<Position, Long> {
    List<Position> findByUserIsNull(Pageable pageable);
}
