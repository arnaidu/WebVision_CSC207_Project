package com.csc.spring.service.implementations;

import com.csc.spring.db.InterviewRepo;
import com.csc.spring.models.Interview;
import com.csc.spring.models.User;
import com.csc.spring.observer.NotificationEvent;
import com.csc.spring.service.InterviewerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.lang.NonNull;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class InterviewerServiceImpl implements InterviewerService, ApplicationEventPublisherAware {

    private ApplicationEventPublisher publisher; // Inject a publisher to publish notifications

    @Autowired
    private InterviewRepo interviewRepo;    // Inject interview repo

    /**
     * get Interview
     * @param interviewerEmail
     * @return
     */
    @Override
    public List<Interview> getInterviews(String interviewerEmail) {
        return interviewRepo.getAllByInterviewerEmail(interviewerEmail);
    }


    /**
     * Interviewer Recommend capability
     * @param message
     * @param toUserId
     */
    @Override
    public void recommend(String message, Long toUserId) {
        // Note: can create different recommend methods to handle different ways of recommending a notification

        NotificationEvent not = new NotificationEvent(this, message, toUserId);
        publisher.publishEvent(not);


    }

    /**
     * Interviewer rejection capability
     * @param message
     * @param toUserId
     */
    @Override
    public void reject(Interview interview, String message, Long toUserId) {

        interviewRepo.delete(interview);
        NotificationEvent not = new NotificationEvent(this, message, toUserId);
        publisher.publishEvent(not);

    }

    @Override
    /**
     * Allow interviewer to save an interview
     */
    public void save(Interview interview){
        interviewRepo.save(interview);
    }

    /**
     * Get the description of interview
     * @param interview
     * @return
     */
    public String getDescription(Interview interview) {
        return interview.getDescription();
    }


    /**
     * This is a publisher which will publish an event. It is like the notifyAllObservers from observable
     * @param publisher
     */
    @Override
    public void setApplicationEventPublisher(@NonNull ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }
}
