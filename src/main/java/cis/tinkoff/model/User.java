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

import java.util.ArrayList;
import java.util.List;

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
    private List<ContactInformation> contacts = new ArrayList<>();

    @Relation(
            value = Relation.Kind.ONE_TO_MANY,
            cascade = Relation.Cascade.ALL,
            mappedBy = "user"
    )
    @Nullable
    private List<Resume> resumes = new ArrayList<>();

    @Relation(
            value = Relation.Kind.ONE_TO_MANY,
            cascade = Relation.Cascade.PERSIST,
            mappedBy = "user"
    )
    @Nullable
    private List<Team> teams = new ArrayList<>();

    @Relation(
            value = Relation.Kind.ONE_TO_MANY,
            cascade = Relation.Cascade.ALL,
            mappedBy = "user"
    )
    @Nullable
    private List<Participant> participants = new ArrayList<>();

    @Relation(
            value = Relation.Kind.ONE_TO_MANY,
            cascade = Relation.Cascade.ALL,
            mappedBy = "user"
    )
    @Nullable
    private List<TeamRequest> requests = new ArrayList<>();

    public User(Long id,
                String login,
                String password,
                String fullName,
                @Nullable String email,
                @Nullable String pictureLink,
                @Nullable List<ContactInformation> contacts,
                @Nullable List<Resume> resumes,
                @Nullable List<Team> teams,
                @Nullable List<Participant> participants,
                @Nullable List<TeamRequest> requests) {
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

