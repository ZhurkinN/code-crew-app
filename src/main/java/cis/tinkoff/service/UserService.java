package cis.tinkoff.service;

import cis.tinkoff.model.User;
import cis.tinkoff.support.exceptions.RecordNotFoundException;
import cis.tinkoff.support.exceptions.UserAlreadyExistsException;

public interface UserService {

    User save(User user);

    Iterable<User> getAll();

    User getById(Long id) throws RecordNotFoundException;

    void delete(Long id);

    User update(Long id,
                User user);

    User register(String email,
                  String password,
                  String name,
                  String surname) throws UserAlreadyExistsException;

    User getByEmail(String email) throws RecordNotFoundException;

    void softDelete(Long id);
}
