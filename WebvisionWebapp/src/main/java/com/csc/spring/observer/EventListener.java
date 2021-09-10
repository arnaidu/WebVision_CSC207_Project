package com.csc.spring.observer;

import com.csc.spring.Application;
import com.csc.spring.db.NotificationRepo;
import com.csc.spring.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.lang.NonNullApi;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;


@Component
public class EventListener implements ApplicationListener<NotificationEvent> {

    private NotificationService notificationService;

    /**
     * This is to set up an event listener object
     * @param service
     */
    @Autowired
    public EventListener(NotificationService service){
        this.notificationService = service;
    }

    /**
     * This is like update for an observer and will execute once an event is published
     * @param event
     */
    @Override
    public void onApplicationEvent(NotificationEvent event) {

        notificationService.addNotification(event.getMessage(), event.getToUserId());

    }

}
