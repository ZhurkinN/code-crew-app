package cis.tinkoff.support.mapper;

import cis.tinkoff.controller.model.UserDTO;
import cis.tinkoff.model.Project;
import cis.tinkoff.model.User;
import cis.tinkoff.support.mapper.generic.GenericMapper;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.Objects;

@Singleton
public class UserMapper extends GenericMapper<User, UserDTO> {

    public UserDTO toDtoWithProjects(User user,
                                     List<Project> projects) {

        UserDTO dto = toDto(user);
        dto.setProjects(projects);
        return dto;
    }

    @Override
    public UserDTO toDto(User user) {
        if (Objects.isNull(user)) {
            return null;
        }

        UserDTO dto = new UserDTO()
                .setEmail(user.getEmail())
                .setName(user.getName())
                .setSurname(user.getSurname())
                .setMainInformation(user.getMainInformation())
                .setPictureLink(user.getPictureLink())
                .setContacts(user.getContacts())
                .setResumes(user.getResumes())
                .setLeadProjects(user.getLeadProjects())
                .setPositions(user.getPositions());
        setGenericFields(user, dto);
        return dto;
    }
}
