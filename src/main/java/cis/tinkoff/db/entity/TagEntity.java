package cis.tinkoff.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tags")
@Entity
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
