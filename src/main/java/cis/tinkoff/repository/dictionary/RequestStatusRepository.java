package cis.tinkoff.repository.dictionary;

import cis.tinkoff.model.dictionary.RequestStatusDictionary;
import cis.tinkoff.model.enumerated.RequestStatus;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface RequestStatusRepository extends CrudRepository<RequestStatusDictionary, RequestStatus> {

}
