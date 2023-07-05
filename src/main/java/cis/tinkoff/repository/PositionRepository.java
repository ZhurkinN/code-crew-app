package cis.tinkoff.repository;

import cis.tinkoff.model.Position;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.PageableRepository;
import io.micronaut.data.repository.jpa.JpaSpecificationExecutor;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface PositionRepository extends PageableRepository<Position, Long>, JpaSpecificationExecutor<Position> {
}
