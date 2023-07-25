package cis.tinkoff.controller;

import cis.tinkoff.controller.model.NotificationDTO;
import cis.tinkoff.service.NotificationService;
import cis.tinkoff.service.event.NotificationEvent;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.websocket.WebSocketSession;
import io.micronaut.websocket.annotation.OnClose;
import io.micronaut.websocket.annotation.OnError;
import io.micronaut.websocket.annotation.OnOpen;
import io.micronaut.websocket.annotation.ServerWebSocket;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Secured(SecurityRule.IS_ANONYMOUS)
@ServerWebSocket("/ws/notifications/{userLogin}")
@RequiredArgsConstructor
public class NotificationWebSocketController implements ApplicationEventListener<NotificationEvent> {

    private final NotificationService notificationService;
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

    public void publishMessage(List<NotificationDTO> message, WebSocketSession session) {
        session.sendSync(message, MediaType.APPLICATION_JSON_TYPE);
    }

    @OnError
    public void onError(Throwable error, @PathVariable String userLogin) throws Throwable {
        throw error;
    }

    @OnClose
    public void onClose(@PathVariable String userLogin) {
        setUserLogin(null);
        setSession(null);
    }

}
