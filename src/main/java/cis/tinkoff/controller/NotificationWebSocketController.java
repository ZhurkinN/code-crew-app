package cis.tinkoff.controller;

import cis.tinkoff.controller.model.NotificationDTO;
import cis.tinkoff.controller.model.custom.NotificationRequestDTO;
import cis.tinkoff.service.NotificationService;
import cis.tinkoff.service.event.NotificationEvent;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.http.MediaType;
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
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Secured(SecurityRule.IS_AUTHENTICATED)
@ServerWebSocket("/ws/notifications")
public class NotificationWebSocketController implements ApplicationEventListener<NotificationEvent> {
    private final NotificationService notificationService;
    private final SecurityService securityService;
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void onApplicationEvent(NotificationEvent event) {
        String userLogin = extractUserLogin();
        List<NotificationDTO> message = notificationService.getLatestUserNotificationsByLogin(userLogin);

        for (String webSocketSessionId : sessions.keySet()) {
            publishMessage(message, sessions.get(webSocketSessionId));
        }
    }

    @OnOpen
    public void onOpen(WebSocketSession session) {
        sessions.put(session.getId(), session);

        String userLogin = extractUserLogin();
        List<NotificationDTO> message = notificationService.getLatestUserNotificationsByLogin(userLogin);

        publishMessage(message, session);
    }

    @OnMessage
    public void onMessage(NotificationRequestDTO requestDTO, WebSocketSession session) {
        String userLogin = extractUserLogin();

        if (!requestDTO.getDelete()) {
            List<NotificationDTO> message = notificationService.getUserNotificationsByLogin(userLogin, requestDTO);
            publishMessage(message, session);
        } else {
            notificationService.deleteNotificationById(requestDTO.getNotificationId());
            session.sendSync("OK", MediaType.APPLICATION_JSON_TYPE);
        }
    }

    public void publishMessage(List<NotificationDTO> message, WebSocketSession session) {
        session.sendSync(message, MediaType.APPLICATION_JSON_TYPE);
    }

    @OnError
    public void onError(Throwable error) {
    }

    @OnClose
    public void onClose(CloseReason closeReason, WebSocketSession session) {
        session.remove(session.getId());
    }

    private String extractUserLogin() {
        Authentication authentication = securityService.getAuthentication().get();
        return authentication.getName();
    }
}
