package cis.tinkoff.controller;

import cis.tinkoff.controller.model.NotificationDTO;
import cis.tinkoff.model.Notification;
import cis.tinkoff.service.NotificationService;
import cis.tinkoff.service.event.NotificationEvent;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.websocket.WebSocketSession;
import io.micronaut.websocket.annotation.*;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Setter
@Secured(SecurityRule.IS_ANONYMOUS)
@ServerWebSocket("/api/v1/notifications/{userLogin}")
@RequiredArgsConstructor
public class NotificationWebSocketController implements ApplicationEventListener<NotificationEvent> {

    private final NotificationService notificationService;
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void onApplicationEvent(NotificationEvent event) {
        Notification newNotification = (Notification) event.getSource();
        String userLogin = newNotification.getUser().getEmail();
        if (Objects.nonNull(sessions.get(userLogin))) {
            NotificationDTO message = notificationService.getNotificationById(newNotification.getId());

            publishMessage(List.of(message), sessions.get(userLogin));
        }
    }

    @OnOpen
    public void onOpen(WebSocketSession session, @PathVariable String userLogin) {
        sessions.put(userLogin, session);
        List<NotificationDTO> message = notificationService.getLatestUserNotificationsByLogin(userLogin);

        publishMessage(message, session);
    }

    @OnMessage
    public void onMessage(Long notificationId, @PathVariable String userLogin) {
        notificationService.deleteNotificationById(notificationId);
    }

    public void publishMessage(List<NotificationDTO> message, WebSocketSession session) {
        session.sendSync(message, MediaType.APPLICATION_JSON_TYPE);
    }

    @OnError
    public void onError(Throwable error, @PathVariable String userLogin) throws Throwable {
        throw error;
    }

    @OnClose
    public void onClose(@PathVariable String userLogin) {
        sessions.remove(userLogin);
    }

}
