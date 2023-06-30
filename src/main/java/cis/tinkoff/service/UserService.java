package cis.tinkoff.service;

import cis.tinkoff.model.User;
import cis.tinkoff.support.RecordNotFoundException;

public interface UserService {

    User save(User user);

    Iterable<User> getAll();

    User getById(Long id) throws RecordNotFoundException;

    void delete(Long id);

    User update(Long id,
                User user);
}
