package cis.tinkoff.repository;

import cis.tinkoff.model.Position;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.PageableRepository;
import io.micronaut.data.repository.jpa.JpaSpecificationExecutor;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface PositionRepository extends PageableRepository<Position, Long>, JpaSpecificationExecutor<Position> {
//    @Query(value = "SELECT position_ FROM positions position_ ", countQuery = "SELECT count(position_) FROM positions position_ ")
//    @Join(value = "customer", type = Join.Type.FETCH)
    Page<Position> findAll(Pageable pageable);
}
