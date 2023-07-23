package cis.tinkoff.service.event;

import io.micronaut.context.event.ApplicationEvent;

public class NotificationEvent extends ApplicationEvent {

    public NotificationEvent(Object source) {
        super(source);
    }
}
