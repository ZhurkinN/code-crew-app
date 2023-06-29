package cis.tinkoff.db.entity;

import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.Relation;
import io.micronaut.data.jdbc.annotation.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedEntity(alias = "tbl_lobbies")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LobbyEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Relation(value = Relation.Kind.MANY_TO_ONE, cascade = Relation.Cascade.ALL)
    @JoinColumn(name = "owner_id")
    private UserEntity owner;

    private String theme;
    private String description;
}
