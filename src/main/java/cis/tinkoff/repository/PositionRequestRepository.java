package cis.tinkoff.repository;

import cis.tinkoff.model.Position;
import cis.tinkoff.model.PositionRequest;
import cis.tinkoff.model.Resume;
import cis.tinkoff.model.dictionary.RequestStatusDictionary;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.Join;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface PositionRequestRepository extends CrudRepository<PositionRequest, Long> {

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
    List<PositionRequest> findAllByPositionAndStatusAndIsDeletedFalseAndIsInvite(Position position,
                                                                                 RequestStatusDictionary status,
                                                                                 boolean isInvite);

    @Join(value = "status", type = Join.Type.FETCH)
    @Join(value = "resume", type = Join.Type.FETCH)
    @Join(value = "resume.direction", type = Join.Type.FETCH)
    List<PositionRequest> findAllByPositionAndStatusNotEqualsAndIsDeletedFalse(Position position,
                                                                               RequestStatusDictionary status);

    @Join(value = "status", type = Join.Type.FETCH)
    @Join(value = "position", type = Join.Type.FETCH)
    @Join(value = "position.direction", type = Join.Type.FETCH)
    List<PositionRequest> findAllByResumeAndStatusAndIsDeletedFalseAndIsInvite(Resume resume,
                                                                               RequestStatusDictionary status,
                                                                               boolean isInvite);

    @Join(value = "status", type = Join.Type.FETCH)
    @Join(value = "position", type = Join.Type.FETCH)
    @Join(value = "position.direction", type = Join.Type.FETCH)
    List<PositionRequest> findAllByResumeAndStatusNotEqualsAndIsDeletedFalse(Resume resume,
                                                                             RequestStatusDictionary status);

    @Join(value = "position", type = Join.Type.FETCH)
    @Join(value = "position.direction", type = Join.Type.FETCH)
    @Join(value = "position.project", type = Join.Type.FETCH)
    @Join(value = "position.project.leader", type = Join.Type.FETCH)
    @Join(value = "resume", type = Join.Type.FETCH)
    @Join(value = "resume.direction", type = Join.Type.FETCH)
    @Join(value = "resume.user", type = Join.Type.FETCH)
    @Join(value = "status", type = Join.Type.FETCH)
    @Join(value = "notifications", type = Join.Type.LEFT_FETCH)
    Optional<PositionRequest> findByIdAndIsDeletedFalse(@NotNull Long id);

}
