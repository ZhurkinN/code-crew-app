package cis.tinkoff.service.impl;

import cis.tinkoff.model.User;
import cis.tinkoff.repository.UserRepository;
import cis.tinkoff.service.UserService;
import cis.tinkoff.support.RecordNotFoundException;
import io.micronaut.context.annotation.Primary;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

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
                .orElseThrow(RecordNotFoundException::new);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User update(Long id, User user) {
        user.setId(id);
        return userRepository.save(user);
    }
}
