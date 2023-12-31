package cis.tinkoff.service.impl;

import cis.tinkoff.controller.model.UserDTO;
import cis.tinkoff.model.Project;
import cis.tinkoff.model.Resume;
import cis.tinkoff.model.User;
import cis.tinkoff.model.enumerated.RequestStatus;
import cis.tinkoff.repository.ProjectRepository;
import cis.tinkoff.repository.ResumeRepository;
import cis.tinkoff.repository.UserRepository;
import cis.tinkoff.service.UserService;
import cis.tinkoff.support.exceptions.RecordNotFoundException;
import cis.tinkoff.support.exceptions.UserAlreadyExistsException;
import cis.tinkoff.support.mapper.UserMapper;
import io.micrometer.core.annotation.Timed;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.List;

import static cis.tinkoff.support.exceptions.constants.LoggedErrorMessageKeeper.USER_NOT_FOUND;
import static cis.tinkoff.support.exceptions.constants.LoggedErrorMessageKeeper.USER_NOT_FOUND_BY_EMAIL;

@Singleton
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ResumeRepository resumeRepository;
    private final ProjectRepository projectRepository;
    private final UserMapper userMapper;

    @Timed(
            value = "cis.tinkoff.service.impl.userServiceImpl.getById",
            percentiles = {0.5, 0.95, 0.99},
            description = "Finds user by id"
    )
    @Override
    public UserDTO getById(Long id) {

        User user = userRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new RecordNotFoundException(USER_NOT_FOUND, id));

        return setOtherModelsData(user);
    }

    @Override
    public UserDTO getByEmail(String email) {

        User user = userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new RecordNotFoundException(USER_NOT_FOUND_BY_EMAIL, email));

        return setOtherModelsData(user);
    }

    @Override
    public User getByIdWithoutProjects(Long id) {
        return userRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new RecordNotFoundException(USER_NOT_FOUND, id));
    }

    @Override
    public User getByEmailWithoutProjects(String email) {
        return userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new RecordNotFoundException(USER_NOT_FOUND_BY_EMAIL, email));
    }

    @Override
    public User register(String email,
                         String password,
                         String name,
                         String surname) {

        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException(email);
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
                       String mainInformation) {

        User user = userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new RecordNotFoundException(USER_NOT_FOUND_BY_EMAIL, email));
        user.setName(name)
                .setSurname(surname)
                .setMainInformation(mainInformation)
                .setContacts(contacts);

        return userRepository.update(user);
    }

    @Override
    @Transactional
    public void softDelete(String email) {

        User user = userRepository.getByEmail(email);
        user.setIsDeleted(true);
        user.getResumes().forEach(e -> {
            e.setIsDeleted(true);
            resumeRepository.deleteActiveRequestsByResumeIdAndRequestStatusId(e.getId(), RequestStatus.IN_CONSIDERATION);
        });

        userRepository.update(user);
        userRepository.deleteUserFromAllPositionsByUserId(user.getId());
    }

    private UserDTO setOtherModelsData(User user) {

        List<Resume> userResumes = resumeRepository.findByUserAndIsDeletedFalseAndIsActiveTrue(user);
        List<Project> userProjects = projectRepository.getByPositionsUserEmailAndIsDeletedFalse(user.getEmail());
        user.setResumes(userResumes);

        return userMapper.toDtoWithProjects(user, userProjects);
    }

}
