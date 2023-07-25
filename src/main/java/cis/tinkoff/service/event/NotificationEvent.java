package cis.tinkoff.service.event;

import cis.tinkoff.model.Notification;
import io.micronaut.context.event.ApplicationEvent;

public class NotificationEvent extends ApplicationEvent {

    public NotificationEvent(Notification source) {
        super(source);
    }
}
