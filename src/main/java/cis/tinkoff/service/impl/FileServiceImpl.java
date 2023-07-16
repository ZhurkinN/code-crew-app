package cis.tinkoff.service.impl;

import cis.tinkoff.model.User;
import cis.tinkoff.repository.UserRepository;
import cis.tinkoff.service.FileService;
import cis.tinkoff.support.exceptions.BadAvatarPathException;
import cis.tinkoff.support.exceptions.BadMediaTypeException;
import cis.tinkoff.support.exceptions.RecordNotFoundException;
import io.micronaut.http.MediaType;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.micronaut.http.server.types.files.StreamedFile;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static cis.tinkoff.support.exceptions.constants.ErrorDisplayMessageKeeper.USER_NOT_FOUND;
import static cis.tinkoff.support.helper.FileHandler.*;

@Singleton
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private static final String STORAGE_PATH = "src/main/resources/static/images/";

    private final UserRepository userRepository;

    @Override
    public String saveProfilePicture(CompletedFileUpload file,
                                     String userEmail) throws BadMediaTypeException, IOException {

        validateFileSize(file);
        validateFileMediaType(file);
        String filename = buildFilename(userEmail);
        savePicture(file, filename);

        return filename;
    }

    @Override
    public StreamedFile getProfilePicture(Long userId) {

        User user = userRepository.findByIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new RecordNotFoundException(USER_NOT_FOUND));

        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(STORAGE_PATH + buildFilename(user.getEmail()));
        } catch (FileNotFoundException e) {
            throw new BadAvatarPathException("Not found");
        }

        return new StreamedFile(fileInputStream, MediaType.IMAGE_PNG_TYPE);
    }

    private void savePicture(CompletedFileUpload file,
                             String filename) throws IOException {

        FileOutputStream fileOutputStream = new FileOutputStream(STORAGE_PATH + filename);
        fileOutputStream.write(file.getBytes());
        fileOutputStream.close();
    }

}
