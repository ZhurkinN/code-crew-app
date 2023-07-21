package cis.tinkoff.repository;

import cis.tinkoff.model.Notification;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface NotificationRepository extends CrudRepository<Notification, Long> {
}
