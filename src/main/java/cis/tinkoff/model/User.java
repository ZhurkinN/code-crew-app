package cis.tinkoff.model;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.Relation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@MappedEntity(value = "users")
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String login;
    private String password;
    private String fullName;
    @Nullable
    private String email;
    @Nullable
    private String pictureLink;

    @Relation(
            value = Relation.Kind.ONE_TO_MANY,
            cascade = Relation.Cascade.ALL,
            mappedBy = "user"
    )
    @Nullable
    private Set<ContactInformation> contacts = new HashSet<>();

    @Relation(
            value = Relation.Kind.ONE_TO_MANY,
            cascade = Relation.Cascade.ALL,
            mappedBy = "user"
    )
    @Nullable
    private Set<Resume> resumes = new HashSet<>();

    @Relation(
            value = Relation.Kind.ONE_TO_MANY,
            cascade = Relation.Cascade.PERSIST,
            mappedBy = "user"
    )
    @Nullable
    private Set<Team> teams = new HashSet<>();

    @Relation(
            value = Relation.Kind.ONE_TO_MANY,
            cascade = Relation.Cascade.ALL,
            mappedBy = "user"
    )
    @Nullable
    private Set<Participant> participants = new HashSet<>();

    @Relation(
            value = Relation.Kind.ONE_TO_MANY,
            cascade = Relation.Cascade.ALL,
            mappedBy = "user"
    )
    @Nullable
    private Set<TeamRequest> requests = new HashSet<>();

    public User(Long id,
                String login,
                String password,
                String fullName,
                @Nullable String email,
                @Nullable String pictureLink,
                @Nullable Set<ContactInformation> contacts,
                @Nullable Set<Resume> resumes,
                @Nullable Set<Team> teams,
                @Nullable Set<Participant> participants,
                @Nullable Set<TeamRequest> requests) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.pictureLink = pictureLink;
        this.contacts = contacts;
        this.resumes = resumes;
        this.teams = teams;
        this.participants = participants;
        this.requests = requests;
    }
}

