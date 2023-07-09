package cis.tinkoff.model;

import cis.tinkoff.model.generic.GenericModel;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.Relation;
import io.micronaut.data.annotation.TypeDef;
import io.micronaut.data.jdbc.annotation.JoinColumn;
import io.micronaut.data.jdbc.annotation.JoinTable;
import io.micronaut.data.model.DataType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@MappedEntity(value = "users")
public class User extends GenericModel {

    private String email;
    private String password;
    private String name;
    private String surname;
    @Nullable
    private String mainInformation;
    @Nullable
    private String pictureLink;

    @Nullable
    @TypeDef(type = DataType.STRING_ARRAY)
    private List<String> contacts = new ArrayList<>();

    @Relation(
            value = Relation.Kind.ONE_TO_MANY,
            cascade = Relation.Cascade.ALL,
            mappedBy = "user"
    )
    @Nullable
    private List<Resume> resumes;

    @Relation(
            value = Relation.Kind.ONE_TO_MANY,
            cascade = Relation.Cascade.ALL,
            mappedBy = "leader"
    )
    @Nullable
    private List<Project> leadProjects = new ArrayList<>();

    @Relation(
            value = Relation.Kind.ONE_TO_MANY,
            cascade = Relation.Cascade.ALL,
            mappedBy = "user"
    )
    @Nullable
    private List<Position> positions = new ArrayList<>();

    @Relation(
            value = Relation.Kind.MANY_TO_MANY,
            cascade = Relation.Cascade.ALL
    )
    @JoinTable(
            name = "project_members",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    @Nullable
    private List<Project> projects = new ArrayList<>();

    public User(Long id,
                LocalDateTime createdWhen,
                Boolean isDeleted,
                String email,
                String password,
                String name,
                String surname,
                @Nullable String mainInformation,
                @Nullable String pictureLink,
                @Nullable List<String> contacts,
                @Nullable List<Resume> resumes,
                @Nullable List<Project> leadProjects,
                @Nullable List<Position> positions,
                @Nullable List<Project> projects) {
        super(id, createdWhen, isDeleted);
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.mainInformation = mainInformation;
        this.pictureLink = pictureLink;
        this.contacts = contacts;
        this.resumes = resumes;
        this.leadProjects = leadProjects;
        this.positions = positions;
        this.projects = projects;
    }
}

