package cis.tinkoff.controller;

import cis.tinkoff.model.Notification;
import cis.tinkoff.service.NotificationService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Tag(name = "Notifications", description = "All actions with notifications.")
@Controller("/api/v1/notifications")
@Secured(SecurityRule.IS_AUTHENTICATED)
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(method = "findAll", description = "Finds all notifications")
    @Get(produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<Notification>> findAll() {
        return HttpResponse.ok(notificationService.getAll());
    }
}
