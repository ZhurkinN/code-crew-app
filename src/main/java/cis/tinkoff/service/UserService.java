package cis.tinkoff.service;

import cis.tinkoff.model.User;
import io.micrometer.core.annotation.Timed;

import java.util.List;

public interface UserService {

    User getById(Long id);

    User getByEmail(String email);

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

    List<User> findUsersByIdsOrElseThrow(List<Long> ids);
}
