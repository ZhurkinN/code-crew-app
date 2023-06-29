package cis.tinkoff.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "lobbies")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LobbyEntity {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private UserEntity owner;

    private String theme;
    private String description;
}
