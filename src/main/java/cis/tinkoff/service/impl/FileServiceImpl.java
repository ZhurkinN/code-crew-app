package cis.tinkoff.service.impl;

import cis.tinkoff.model.User;
import cis.tinkoff.repository.UserRepository;
import cis.tinkoff.service.FileService;
import cis.tinkoff.support.exceptions.ProfilePictureNotFoundException;
import cis.tinkoff.support.exceptions.RecordNotFoundException;
import cis.tinkoff.support.exceptions.UnavailableMediaTypeException;
import io.micronaut.context.annotation.Property;
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
import static cis.tinkoff.support.helper.FileHandler.buildFilename;
import static cis.tinkoff.support.helper.FileHandler.validateFileMediaType;

@Singleton
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final UserRepository userRepository;

    @Property(name = "micronaut.router.static-resources.pictures.path")
    private String storagePath;

    @Property(name = "micronaut.server.multipart.max-file-size")
    private long maxFileSize;

    @Property(name = "micronaut.server.multipart.common-file-extension")
    private String commonExtension;

    @Override
    public String saveProfilePicture(CompletedFileUpload file,
                                     String userEmail) throws UnavailableMediaTypeException, IOException {

        validateFileMediaType(file, userEmail);
        String filename = buildFilename(userEmail, commonExtension);
        savePicture(file, filename);

        return filename;
    }

    @Override
    public StreamedFile getProfilePicture(Long userId) {

        User user = userRepository.findByIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new RecordNotFoundException(USER_NOT_FOUND));

        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(storagePath + buildFilename(user.getEmail(), commonExtension));
        } catch (FileNotFoundException e) {
            throw new ProfilePictureNotFoundException(userId);
        }

        return new StreamedFile(fileInputStream, MediaType.IMAGE_PNG_TYPE);
    }

    private void savePicture(CompletedFileUpload file,
                             String filename) throws IOException {

        FileOutputStream fileOutputStream = new FileOutputStream(storagePath + filename);
        fileOutputStream.write(file.getBytes());
        fileOutputStream.close();
    }

}
