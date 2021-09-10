package com.csc.spring.service.implementations;

import com.csc.spring.db.NotificationRepo;
import com.csc.spring.models.Notifications;
import com.csc.spring.models.User;
import com.csc.spring.observer.NotificationEvent;
import com.csc.spring.service.NotificationService;
import com.csc.spring.util.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {


    private NotificationRepo notificationRepo;

    /**
     * Inject Notification repositories
     * @param repo
     */
    @Autowired
    public NotificationServiceImpl(NotificationRepo repo){
        this.notificationRepo = repo;
    }


    /**
     * add notification object to single user
     * @param message
     * @param toUserId
     */
    @Override
    public void addNotification(String message, Long toUserId) {
        Notifications not = new Notifications(message, toUserId);
        notificationRepo.save(not);
    }


    /**
     * add notification object to single user
     * @param message
     * @param toUserId
     */

    @Override
    public void addNotification(String message, Collection<User> toUserId){
        for (User user: toUserId){
            Notifications not = new Notifications(message, user.getId());
            notificationRepo.save(not);
        }

    }

    /**
     *
     * @param toUserId
     * @return list of all notifications
     */
    @Override
    public List<Notifications> getNotifications(Long toUserId) {

        return notificationRepo.findAllByToUserId(toUserId);

    }

}
