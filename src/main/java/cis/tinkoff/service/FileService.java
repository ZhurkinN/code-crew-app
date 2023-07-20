package cis.tinkoff.service;

import io.micronaut.http.multipart.CompletedFileUpload;
import io.micronaut.http.server.types.files.StreamedFile;

public interface FileService {

    String saveProfilePicture(CompletedFileUpload file,
                              String userEmail);

    StreamedFile getProfilePicture(Long userId);
}
