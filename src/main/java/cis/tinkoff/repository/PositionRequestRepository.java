package cis.tinkoff.repository;

import cis.tinkoff.model.PositionRequest;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface PositionRequestRepository extends CrudRepository<PositionRequest, Long> {
}
