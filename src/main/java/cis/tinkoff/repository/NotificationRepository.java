package cis.tinkoff.repository;

import cis.tinkoff.model.Notification;
import io.micronaut.data.annotation.Join;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface NotificationRepository extends CrudRepository<Notification, Long> {

    @Override
    @Join(value = "type", type = Join.Type.FETCH)
    @Join(value = "request", type = Join.Type.FETCH)
    @Join(value = "request.resume", type = Join.Type.FETCH)
    @Join(value = "request.position", type = Join.Type.FETCH)
    @Join(value = "request.position.project", type = Join.Type.FETCH)
    @Join(value = "user", type = Join.Type.FETCH)
    Optional<Notification> findById(Long id);

    @Join(value = "type", type = Join.Type.FETCH)
    @Join(value = "request", type = Join.Type.FETCH)
    @Join(value = "request.resume", type = Join.Type.FETCH)
    @Join(value = "request.position", type = Join.Type.FETCH)
    @Join(value = "request.position.project", type = Join.Type.FETCH)
    @Join(value = "user", type = Join.Type.FETCH)
    Page<Notification> findByUserId(Long user_id, Pageable pageable);
}
