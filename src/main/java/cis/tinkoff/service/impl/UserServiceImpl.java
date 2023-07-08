package cis.tinkoff.service.impl;

import cis.tinkoff.model.Resume;
import cis.tinkoff.model.User;
import cis.tinkoff.repository.ResumeRepository;
import cis.tinkoff.repository.UserRepository;
import cis.tinkoff.service.UserService;
import cis.tinkoff.support.exceptions.DeletedRecordFoundException;
import cis.tinkoff.support.exceptions.RecordNotFoundException;
import cis.tinkoff.support.exceptions.UserAlreadyExistsException;
import io.micronaut.context.annotation.Primary;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static cis.tinkoff.support.exceptions.constants.ErrorDisplayMessageKeeper.*;

@Primary
@Singleton
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ResumeRepository resumeRepository;

    @Override
    public Iterable<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(Long id) throws RecordNotFoundException, DeletedRecordFoundException {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(RECORD_NOT_FOUND));
        if (user.getIsDeleted()) {
            throw new DeletedRecordFoundException(DELETED_RECORD_FOUND);
        }
        List<Resume> userResumes = resumeRepository.findByUser(user);
        user.setResumes(userResumes);
        return user;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User update(String email,
                       String name,
                       String surname,
                       List<String> contacts,
                       String pictureLink,
                       String mainInformation) throws RecordNotFoundException {

        if (!userRepository.existsByEmail(email)) {
            throw new RecordNotFoundException(RECORD_NOT_FOUND);
        }
        User user = userRepository.findByEmail(email).orElseThrow();
        user.setName(name)
                .setSurname(surname)
                .setPictureLink(pictureLink)
                .setMainInformation(mainInformation);
        if (!contacts.isEmpty()) {
            user.setContacts(contacts);
        }
        return userRepository.update(user);
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
        return userRepository.save(user);
    }

    @Override
    public User getByEmail(String email) throws RecordNotFoundException, DeletedRecordFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RecordNotFoundException(RECORD_NOT_FOUND));
        if (user.getIsDeleted()) {
            throw new DeletedRecordFoundException(DELETED_RECORD_FOUND);
        }
        List<Resume> userResumes = resumeRepository.findByUser(user);
        user.setResumes(userResumes);
        return user;
    }

    @Override
    public void softDelete(String email) {
        userRepository.updateByEmail(email, true);
    }
}
