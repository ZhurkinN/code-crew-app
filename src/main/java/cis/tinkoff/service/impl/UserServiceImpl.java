package cis.tinkoff.service.impl;

import cis.tinkoff.model.Project;
import cis.tinkoff.model.Resume;
import cis.tinkoff.model.User;
import cis.tinkoff.model.generic.GenericModel;
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
    public List<User> getAll() {

        List<User> users = (List<User>) userRepository.findAll();
        users.forEach(this::setOtherModelsData);
        return users;
    }

    @Override
    public User getById(Long id) throws RecordNotFoundException, DeletedRecordFoundException {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(USER_NOT_FOUND));
        if (user.getIsDeleted()) {
            throw new DeletedRecordFoundException(DELETED_RECORD_FOUND);
        }

        return setOtherModelsData(user);
    }

    @Override
    public User getByEmail(String email) throws RecordNotFoundException, DeletedRecordFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RecordNotFoundException(USER_NOT_FOUND));
        if (user.getIsDeleted()) {
            throw new DeletedRecordFoundException(DELETED_RECORD_FOUND);
        }

        return setOtherModelsData(user);
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
    public User update(String email,
                       String name,
                       String surname,
                       List<String> contacts,
                       String pictureLink,
                       String mainInformation) throws RecordNotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RecordNotFoundException(USER_NOT_FOUND));
        user.setName(name)
                .setSurname(surname)
                .setPictureLink(pictureLink)
                .setMainInformation(mainInformation)
                .setContacts(contacts);

        return userRepository.update(user);
    }

    @Override
    public void softDelete(String email) {
        userRepository.updateByEmail(email, true);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    private User setOtherModelsData(User user) {

        List<Resume> userResumes = resumeRepository.findByUserAndIsDeletedFalse(user);
        List<Project> userProjects = userRepository.findProjectsById(user.getId());
        userProjects.removeIf(GenericModel::getIsDeleted);
        user.setResumes(userResumes);
        user.setProjects(userProjects);

        return user;
    }

}
