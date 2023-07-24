package cis.tinkoff.controller;

import cis.tinkoff.controller.model.NotificationDTO;
import cis.tinkoff.controller.model.custom.NotificationRequestDTO;
import cis.tinkoff.service.NotificationService;
import cis.tinkoff.service.event.NotificationEvent;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.security.utils.SecurityService;
import io.micronaut.websocket.CloseReason;
import io.micronaut.websocket.WebSocketSession;
import io.micronaut.websocket.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Secured(SecurityRule.IS_ANONYMOUS)
@ServerWebSocket("/ws/notifications/{userLogin}")
public class NotificationWebSocketController implements ApplicationEventListener<NotificationEvent> {
    private final NotificationService notificationService;
    private final SecurityService securityService;
//    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private WebSocketSession session;
    private String userLogin;

    @Override
    public void onApplicationEvent(NotificationEvent event) {
        List<NotificationDTO> message = notificationService.getLatestUserNotificationsByLogin(userLogin);
        publishMessage(message, session);
    }

    @OnOpen
    public void onOpen(WebSocketSession session, @PathVariable String userLogin) {
        this.userLogin = userLogin;
        this.session = session;
        List<NotificationDTO> message = notificationService.getLatestUserNotificationsByLogin(userLogin);

        publishMessage(message, session);
    }

    @OnMessage
    public void onMessage(NotificationRequestDTO requestDTO, WebSocketSession session, @PathVariable String userLogin) {
    }

    public void publishMessage(List<NotificationDTO> message, WebSocketSession session) {
        session.sendSync(message, MediaType.APPLICATION_JSON_TYPE);
    }

    @OnError
    public void onError(Throwable error, @PathVariable String userLogin) throws Throwable {
        throw error;
    }

    @OnClose
    public void onClose(CloseReason closeReason, WebSocketSession session, @PathVariable String userLogin) {
    }

    private String getUserLogin(@PathVariable String userLogin) {
        Authentication authentication = securityService.getAuthentication().get();
        return authentication.getName();
    }
}
