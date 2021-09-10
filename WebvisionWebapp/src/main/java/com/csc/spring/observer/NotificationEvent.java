package com.csc.spring.observer;

import org.springframework.context.ApplicationEvent;

public class NotificationEvent extends ApplicationEvent {

    private String message;
    private Long toUserId;

    /**
     * Creates a notification event for a single user (the event is neither an observer or observable)
     * @param source
     * @param message
     * @param toUserId
     */
    public NotificationEvent(Object source, String message, Long toUserId) {
        super(source);
        this.message = message;
        this.toUserId = toUserId;

    }

    /* Getters */
    public String getMessage() {
        return message;
    }
    public Long getToUserId() {
        return toUserId;
    }
}
