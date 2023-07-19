package cis.tinkoff.service;

import cis.tinkoff.support.exceptions.ProfilePictureNotFoundException;
import cis.tinkoff.support.exceptions.UnavailableMediaTypeException;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.micronaut.http.server.types.files.StreamedFile;

import java.io.IOException;

public interface FileService {

    String saveProfilePicture(CompletedFileUpload file,
                              String userEmail) throws UnavailableMediaTypeException, IOException;

    StreamedFile getProfilePicture(Long userId) throws ProfilePictureNotFoundException;
}
