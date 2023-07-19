package cis.tinkoff.repository;

import cis.tinkoff.model.Position;
import cis.tinkoff.model.PositionRequest;
import cis.tinkoff.model.RequestStatusDictionary;
import cis.tinkoff.model.Resume;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.Join;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import io.micronaut.data.repository.jpa.criteria.QuerySpecification;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface PositionRequestRepository extends CrudRepository<PositionRequest, Long> {

    Page<PositionRequest> findAll(QuerySpecification<Position> spec, Pageable pageable);

    @Join(value = "resume", type = Join.Type.FETCH)
    @Join(value = "position", type = Join.Type.FETCH)
    @Join(value = "status", type = Join.Type.FETCH)
    List<PositionRequest> list();

    List<PositionRequest> findByResumeAndIsDeletedFalseAndStatus(Resume resume,
                                                                 RequestStatusDictionary status);

    Position findPositionById(@Id Long id);

    @Join(value = "status", type = Join.Type.FETCH)
    @Join(value = "resume", type = Join.Type.FETCH)
    @Join(value = "resume.direction", type = Join.Type.FETCH)
    List<PositionRequest> findAllByPositionAndIsDeletedFalseAndIsInviteFalse(Position position);

    @Join(value = "status", type = Join.Type.FETCH)
    @Join(value = "position", type = Join.Type.FETCH)
    @Join(value = "position.direction", type = Join.Type.FETCH)
    List<PositionRequest> findAllByResumeAndIsDeletedFalseAndIsInviteTrue(Resume resume);

    @Join(value = "position", type = Join.Type.FETCH)
    @Join(value = "position.project", type = Join.Type.FETCH)
    @Join(value = "resume", type = Join.Type.FETCH)
    @Join(value = "resume.user", type = Join.Type.FETCH)
    @Join(value = "status", type = Join.Type.FETCH)
    Optional<PositionRequest> findByIdAndIsDeletedFalse(@NotNull Long id);

    //    @Join(value = "resume", type = Join.Type.FETCH)
//    @Query(value = "select * from position_request ps join (resume rm join users us on rm.user_id = us.id) on ps.resume_id = rm.id")
//    Page<PositionRequestController> findAllPositionRequestsWithUserResume(Pageable pageable);
}
