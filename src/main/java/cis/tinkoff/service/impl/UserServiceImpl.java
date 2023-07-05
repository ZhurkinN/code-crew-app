package cis.tinkoff.service.impl;

import cis.tinkoff.model.User;
import cis.tinkoff.repository.UserRepository;
import cis.tinkoff.service.UserService;
import cis.tinkoff.support.exceptions.RecordNotFoundException;
import cis.tinkoff.support.exceptions.UserAlreadyExistsException;
import io.micronaut.context.annotation.Primary;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import static cis.tinkoff.support.exceptions.constants.ErrorDisplayMessageKeeper.RECORD_NOT_FOUND;
import static cis.tinkoff.support.exceptions.constants.ErrorDisplayMessageKeeper.USER_ALREADY_EXISTS;

@Primary
@Singleton
public class UserServiceImpl implements UserService {

    @Inject
    private UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Iterable<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(Long id) throws RecordNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(RECORD_NOT_FOUND));
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User update(Long id,
                       User user) {
        user.setId(id);
        return userRepository.save(user);
    }

    @Override
    public User register(String email,
                         String password,
                         String name,
                         String surname) throws UserAlreadyExistsException {

        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException(USER_ALREADY_EXISTS);
        }
        User user = new User()
                .setEmail(email)
                .setPassword(password)
                .setName(name)
                .setSurname(surname);
        User createdUser = userRepository.save(user);
        userRepository.update(createdUser.getId(), new String[0]);
        return createdUser;
    }
}
