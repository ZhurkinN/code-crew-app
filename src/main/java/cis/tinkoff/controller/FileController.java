package cis.tinkoff.controller;


import cis.tinkoff.controller.model.FileDTO;
import cis.tinkoff.service.FileService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.micronaut.http.server.types.files.StreamedFile;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static io.micronaut.http.MediaType.*;

@Slf4j
@Tag(name = "Files", description = "All actions with files in project")
@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("api/v1/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @Post(
            consumes = MULTIPART_FORM_DATA,
            produces = APPLICATION_JSON
    )
    public HttpResponse<FileDTO> uploadProfilePicture(@Part CompletedFileUpload picture,
                                                      Authentication authentication) {

        String email = authentication.getName();
        String path = fileService.saveProfilePicture(picture, email);
        FileDTO responseDto = new FileDTO(path);
        return HttpResponse.ok(responseDto);
    }

    @Get(value = "/{id}", produces = IMAGE_PNG)
    @Secured(SecurityRule.IS_ANONYMOUS)
    public StreamedFile download(@PathVariable("id") Long userId) {

        return fileService.getProfilePicture(userId);
    }
}
