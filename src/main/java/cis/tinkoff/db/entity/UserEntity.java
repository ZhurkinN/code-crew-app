package cis.tinkoff.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.kafka.common.protocol.types.Field;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "users")
@Entity
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
