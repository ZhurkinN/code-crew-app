package cis.tinkoff.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "tbl_lobby_join_request")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LobbyMemberEntity {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "lobby_id", nullable = false)
    private LobbyEntity lobby;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private TagEntity tag;
}
