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
public class MultiEventListener implements ApplicationListener<MultiNotificationEvent> {

    private NotificationService notificationService;

    /**
     * this creates an event listener to handle the case where we want to add notification to a list of users.
     * For example, adding a notification about the job posting being closed for all applicants.
     * @param service
     */
    @Autowired
    public MultiEventListener(NotificationService service){
        this.notificationService = service;
    }

    /**
     * This will get called when an event actually happens where we need to add multiple notifications
     * to the notification board. 
     * @param event
     */
    @Override
    public void onApplicationEvent(MultiNotificationEvent event) {

        notificationService.addNotification(event.getMessage(), event.getUsers());

    }

}
