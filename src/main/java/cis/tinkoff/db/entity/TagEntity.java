package cis.tinkoff.db.entity;

import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedEntity(alias = "tbl_tags")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TagEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String tagName;
}
