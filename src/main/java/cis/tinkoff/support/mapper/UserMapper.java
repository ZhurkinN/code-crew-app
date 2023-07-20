package cis.tinkoff.support.mapper;

import cis.tinkoff.controller.model.UserDTO;
import cis.tinkoff.model.User;
import cis.tinkoff.support.mapper.generic.GenericMapper;
import jakarta.inject.Singleton;

import java.util.Objects;

@Singleton
public class UserMapper extends GenericMapper<User, UserDTO> {

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
                .setPositions(user.getPositions())
                .setProjects(user.getProjects());
        setGenericFields(user, dto);
        return dto;
    }

}
