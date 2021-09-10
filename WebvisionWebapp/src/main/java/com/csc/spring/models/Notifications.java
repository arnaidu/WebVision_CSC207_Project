package com.csc.spring.models;

import com.csc.spring.util.CurrentUser;
import com.csc.spring.util.MyDateFormatter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;


@Entity
@Table(name = "notifications")
public class Notifications {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "notification_id")
    private Long id;

    @Column(name = "date_now")
    private String date;

    @Column(name = "message")
    @Length(min = 0, max = 10000)
    private String message;

    @Column(name = "to_user_id")
    private Long toUserId;

    public Notifications(){}

    /**
     * Create a Notification Object
     * @param message
     * @param toUserId
     */
    public Notifications(String message, Long toUserId){

        this.date = (new MyDateFormatter()).setDate("yyyy-mm-dd hh:mm:ss");
        this.message = message;

        // This keeps track of what user the notification is for (can be current user or another user)
        this.toUserId = toUserId;

    }

    /* Getters */

    public String getDate() {
        return date;
    }
    public String getMessage() {
        return message;
    }
    public Long getToUserId() {
        return toUserId;
    }
    public Long getId() {
        return id;
    }

    /* Setters */

    public void setDate(String date) {
        this.date = date;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }
}
