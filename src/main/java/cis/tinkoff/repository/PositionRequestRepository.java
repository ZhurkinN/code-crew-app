package cis.tinkoff.repository;

import cis.tinkoff.model.Position;
import cis.tinkoff.model.PositionRequest;
import io.micronaut.data.annotation.Join;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import io.micronaut.data.repository.jpa.criteria.QuerySpecification;

import java.util.List;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface PositionRequestRepository extends CrudRepository<PositionRequest, Long> {

    Page<PositionRequest> findAll(QuerySpecification<Position> spec, Pageable pageable);

    @Join(value = "resume", type = Join.Type.FETCH)
    @Join(value = "position", type = Join.Type.FETCH)
    @Join(value = "status", type = Join.Type.FETCH)
    List<PositionRequest> list();

    @Join(value = "resume", type = Join.Type.FETCH)
    @Join(value = "position", type = Join.Type.FETCH)
    @Join(value = "status", type = Join.Type.FETCH)
    List<PositionRequest> findAllByPositionId(Long id);

//    @Join(value = "resume", type = Join.Type.FETCH)
//    @Query(value = "select * from position_request ps join (resume rm join users us on rm.user_id = us.id) on ps.resume_id = rm.id")
//    Page<PositionRequestController> findAllPositionRequestsWithUserResume(Pageable pageable);
}
