package cis.tinkoff.repository;

import cis.tinkoff.model.DirectionDictionary;
import cis.tinkoff.model.Resume;
import cis.tinkoff.model.User;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.Join;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.Sort;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface ResumeRepository extends CrudRepository<Resume, Long> {

    @Join(value = "direction", type = Join.Type.FETCH)
    @Join(value = "user", type = Join.Type.FETCH)
    List<Resume> findByUserAndIsDeletedFalse(User user);

    @Join(value = "direction", type = Join.Type.FETCH)
    @Join(value = "user", type = Join.Type.FETCH)
    List<Resume> findByUserAndIsDeletedFalseAndIsActive(User user,
                                                        boolean isActive);

    @Join(value = "direction", type = Join.Type.FETCH)
    List<Resume> findByUserAndIsDeletedFalseAndIsActiveTrue(User user);

    void updateById(@Id Long id,
                    Boolean isDeleted);

    Boolean getIsActiveById(@Id Long id);

    void updateIsActiveById(@Id Long id,
                            Boolean isActive);

    User getUserById(Long id);

    @Join(value = "direction", type = Join.Type.FETCH)
    @Join(value = "user", type = Join.Type.FETCH)
    Optional<Resume> findByIdAndIsDeletedFalse(@Id Long id);

    @Join(value = "direction", type = Join.Type.FETCH)
    Optional<Resume> getByIdAndIsDeletedFalse(@Id Long id);

    @Join(value = "direction", type = Join.Type.FETCH)
    @Join(value = "user", type = Join.Type.FETCH)
    Optional<Resume> findByIdAndIsDeletedFalseAndIsActiveTrue(@Id Long id);

    @Query(value = """
            SELECT  resume_.* FROM resume resume_
                JOIN dictionary_direction dd ON resume_.direction = dd.direction_name
                JOIN users u on u.id = resume_.user_id
            WHERE resume_.is_active = true
              AND resume_.is_deleted = false
              AND resume_.direction ilike coalesce(:direction, '%')
              AND lower(resume_.skills::text)::text[] @> coalesce(lower(:skills::text)::text[], lower(resume_.skills::text)::text[])
            """,
            nativeQuery = true,
            countQuery = """
                    SELECT  count(resume_.*) FROM resume resume_
                        JOIN dictionary_direction dd ON resume_.direction = dd.direction_name
                        JOIN users u on u.id = resume_.user_id
                    WHERE resume_.is_active = true
                      AND resume_.is_deleted = false
                      AND resume_.direction ilike coalesce(:direction, '%')
                      AND lower(resume_.skills::text)::text[] @> coalesce(lower(:skills::text)::text[], lower(resume_.skills::text)::text[])
                    """)
    Page<Resume> searchAllResumes(@Nullable String direction, @Nullable List<String> skills, Pageable from);

    @Join(value = "direction", type = Join.Type.FETCH)
    @Join(value = "user", type = Join.Type.FETCH)
    List<Resume> findByIdInList(List<Long> id, Sort sort);

    DirectionDictionary getDirectionById(@Id Long id);

}
