package cis.tinkoff.service.event;

public class RequestCreatedEvent extends NotificationEvent {

    public RequestCreatedEvent(Object source) {
        super(source);
    }
}
