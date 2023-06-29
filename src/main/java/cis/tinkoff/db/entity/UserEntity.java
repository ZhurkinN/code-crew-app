package cis.tinkoff.db.entity;

import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedEntity(alias = "tbl_users")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String login;
    private String firstName;
    private String lastName;
    private String middleName;
    private String description;
    private String email;
    private String pictureLink;
}
