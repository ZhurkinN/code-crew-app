package cis.tinkoff.service.impl;

import cis.tinkoff.controller.model.AvatarDTO;
import cis.tinkoff.service.AvatarService;
import cis.tinkoff.support.exceptions.BadAvatarPathException;
import cis.tinkoff.support.exceptions.BadMediaTypeException;
import cis.tinkoff.support.exceptions.RequestEntityTooLargeException;
import io.micronaut.http.MediaType;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.micronaut.http.server.types.files.StreamedFile;
import jakarta.inject.Singleton;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;

@Singleton
public class AvatarServiceImpl implements AvatarService {
    private static final Integer MAX_FILE_SIZE = 5500000;
    private static final String STORAGE_PATH = "src/main/resources/static/pic/";
    private static final String BASE_EXTENSION = "png";
    private static final HashSet<String> ALLOWED_MEDIA_TYPES = new HashSet<>(){{
        add("jpeg");
        add("png");
    }};

    @Override
    public AvatarDTO saveAvatar(CompletedFileUpload file, String userEmail) throws BadMediaTypeException, IOException {
        isNormalSizeFile(file);
        isValidType(file);
        String filename =  buildFilenameWithExtension(userEmail, BASE_EXTENSION);
        saveAvatarToStorage(file, filename);

        return new AvatarDTO(filename);
    }

    @Override
    public StreamedFile getAvatar(String userEmail){
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(STORAGE_PATH + userEmail);
        } catch (FileNotFoundException e) {
            throw new BadAvatarPathException("Not found");
        }
        return new StreamedFile(fileInputStream, MediaType.IMAGE_PNG_TYPE);
    }

    private void saveAvatarToStorage(CompletedFileUpload file, String filename) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(STORAGE_PATH + filename);
        fileOutputStream.write(file.getBytes());
        fileOutputStream.close();
    }

    private void isNormalSizeFile(CompletedFileUpload file) {
        if(file.getSize() == 0){
            throw new BadMediaTypeException("You have not uploaded anything");
        }
        if(file.getSize() > MAX_FILE_SIZE){
            throw new RequestEntityTooLargeException("File is so large for uploading. Size should be less than: " + MAX_FILE_SIZE.toString());
        }
    }

    private void isValidType(CompletedFileUpload file) {
        Optional<MediaType> mediaType = file.getContentType();
        if(mediaType.isEmpty()){
            throw new BadMediaTypeException("The file does not contain an extension");
        }

        String type = mediaType.get().getSubtype();
        if(!ALLOWED_MEDIA_TYPES.contains(type)){
            throw new BadMediaTypeException("This type is not allowed for loading");
        }
    }

    private String buildFilenameWithExtension(String user, String extension){
        return user + "." + extension;
    }
}
