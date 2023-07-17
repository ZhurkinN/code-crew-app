package cis.tinkoff.support.helper;

import cis.tinkoff.support.exceptions.BadMediaTypeException;
import cis.tinkoff.support.exceptions.RequestEntityTooLargeException;
import io.micronaut.context.annotation.Property;
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

    @Property(name = "micronaut.server.multipart.max-file-size")
    private static long maxFileSize;

    @Property(name = "micronaut.server.multipart.common-file-extension")
    private static String commonExtension;

    public static void validateFileMediaType(CompletedFileUpload file) {

        Optional<MediaType> mediaType = file.getContentType();
        if (mediaType.isEmpty()) {
            throw new BadMediaTypeException("The file does not contain an extension");
        }

        String type = mediaType.get().getSubtype();
        if (!ALLOWED_MEDIA_TYPES.contains(type)) {
            throw new BadMediaTypeException("This type is not allowed for loading");
        }
    }

    public static void validateFileSize(CompletedFileUpload file) {

        if (file.getSize() == 0) {
            throw new BadMediaTypeException("You have not uploaded anything");
        }
        if (file.getSize() > maxFileSize) {
            throw new RequestEntityTooLargeException(String.format("File is so large for uploading. Size should be less than: %d bytes", maxFileSize));
        }
    }

    public static String buildFilename(String user) {
        return user + "." + commonExtension;
    }
}
