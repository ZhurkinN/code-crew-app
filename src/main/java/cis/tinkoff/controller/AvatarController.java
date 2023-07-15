package cis.tinkoff.controller;


import cis.tinkoff.controller.model.AvatarDTO;
import cis.tinkoff.service.AvatarService;
import cis.tinkoff.support.exceptions.BadAvatarPathException;
import cis.tinkoff.support.exceptions.RequestEntityTooLargeException;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.micronaut.http.server.types.files.StreamedFile;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.io.*;

import static io.micronaut.http.MediaType.*;

@Tag(name = "Avatars", description = "Actions with user avatars")
@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("/avatar")
@RequiredArgsConstructor
public class AvatarController {

    private final AvatarService avatarService;

    @Post(value = "/{user}", consumes = MULTIPART_FORM_DATA, produces = APPLICATION_JSON)
    public HttpResponse<AvatarDTO> upload(@Part CompletedFileUpload avatar, @PathVariable String user) throws RequestEntityTooLargeException, IOException {

       return HttpResponse.ok(avatarService.saveAvatar(avatar,user));
    }

    @Get(value = "/{user}", produces = IMAGE_PNG)
    public StreamedFile download(@PathVariable String user) throws BadAvatarPathException {

        return avatarService.getAvatar(user);
    }
}
