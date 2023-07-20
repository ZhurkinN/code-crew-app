package cis.tinkoff.support.helper;

import cis.tinkoff.support.exceptions.UnavailableMediaTypeException;
import io.micronaut.http.MediaType;
import io.micronaut.http.multipart.CompletedFileUpload;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileHandler {

    private static final HashSet<String> ALLOWED_MEDIA_TYPES = new HashSet<>() {{
        add("jpeg");
        add("png");
    }};

    public static void validateFileMediaType(CompletedFileUpload file,
                                             String userEmail) {

        Optional<MediaType> mediaType = file.getContentType();
        if (mediaType.isEmpty() || !ALLOWED_MEDIA_TYPES.contains(mediaType.get().getSubtype())) {
            throw new UnavailableMediaTypeException(userEmail, file.getFilename());
        }
    }

    public static String buildFilename(String user,
                                       String commonExtension) {
        return user + "." + commonExtension;
    }
}
