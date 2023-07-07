package cis.tinkoff.repository.dictionary;

import cis.tinkoff.model.DirectionDictionary;
import cis.tinkoff.model.enumerated.Direction;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface DirectionRepository extends CrudRepository<DirectionDictionary, Direction> {

}
