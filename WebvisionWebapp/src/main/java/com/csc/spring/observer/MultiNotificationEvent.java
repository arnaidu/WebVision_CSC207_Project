package com.csc.spring.observer;

import com.csc.spring.models.User;
import org.springframework.context.ApplicationEvent;

import java.util.Collection;

public class MultiNotificationEvent extends ApplicationEvent {

    private String message;
    private Collection<User> users;

    /**
     * Creates a multiple notification event for collection of users (the event is neither an observer or observable)
     * @param source
     * @param message
     * @param users
     */
    public MultiNotificationEvent(Object source, String message, Collection<User> users) {
        super(source);
        this.message = message;
        this.users = users;

    }

    /* Getters */
    public String getMessage() {
        return message;
    }

    public Collection<User> getUsers() {
        return users;
    }
}
