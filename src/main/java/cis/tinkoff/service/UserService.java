package cis.tinkoff.service;

import cis.tinkoff.controller.model.UserDTO;
import cis.tinkoff.model.User;

import java.util.List;

public interface UserService {

    UserDTO getById(Long id);

    UserDTO getByEmail(String email);

    User getByIdWithoutProjects(Long id);

    User getByEmailWithoutProjects(String email);

    User register(String email,
                  String password,
                  String name,
                  String surname);

    User update(String email,
                String name,
                String surname,
                List<String> contacts,
                String pictureLink,
                String mainInformation);

    void softDelete(String email);

}
