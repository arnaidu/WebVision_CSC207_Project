package com.csc.spring.db;

import com.csc.spring.models.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepo extends JpaRepository<Notifications, Long> {
    // Notification database model

    List<Notifications> findAllByToUserId(Long userId);

}
