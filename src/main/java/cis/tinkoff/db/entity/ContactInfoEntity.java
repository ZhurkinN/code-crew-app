package cis.tinkoff.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "contact_information")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ContactInfoEntity {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    private String socialMedia;

    private String link;
}
