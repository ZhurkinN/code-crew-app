package cis.tinkoff.service;

import cis.tinkoff.model.User;
import cis.tinkoff.support.exceptions.DeletedRecordFoundException;
import cis.tinkoff.support.exceptions.RecordNotFoundException;
import cis.tinkoff.support.exceptions.UserAlreadyExistsException;

import java.util.List;

public interface UserService {

    Iterable<User> getAll();

    User getById(Long id) throws RecordNotFoundException, DeletedRecordFoundException;

    void delete(Long id);

    User update(String email,
                String name,
                String surname,
                List<String> contacts,
                String pictureLink,
                String mainInformation) throws RecordNotFoundException;

    User register(String email,
                  String password,
                  String name,
                  String surname) throws UserAlreadyExistsException;

    User getByEmail(String email) throws RecordNotFoundException, DeletedRecordFoundException;

    void softDelete(String email);
}
