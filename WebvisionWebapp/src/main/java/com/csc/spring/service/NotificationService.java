package com.csc.spring.service;

import com.csc.spring.models.Notifications;
import com.csc.spring.models.User;

import java.util.Collection;
import java.util.List;

public interface NotificationService {

    /**
     * To be injected as an intermediary between the views and notification repository
     */

    void addNotification(String message, Long toUserId);

    void addNotification(String message, Collection<User> toUserId);

    List<Notifications> getNotifications(Long userId);

}
