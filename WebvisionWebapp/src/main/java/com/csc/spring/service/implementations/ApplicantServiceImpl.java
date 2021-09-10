package com.csc.spring.service.implementations;

import com.csc.spring.db.ApplicantRepo;
import com.csc.spring.models.Applicant;
import com.csc.spring.models.JobApplication;
import com.csc.spring.observer.NotificationEvent;
import com.csc.spring.service.ApplicantService;
import com.csc.spring.util.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;

@Service
public class ApplicantServiceImpl implements ApplicantService, ApplicationEventPublisherAware {

    private ApplicationEventPublisher publisher; // Inject a publisher to publish notifications

    private final ApplicantRepo applicantRepo;

    /**
     * Inject applicant repo
     * @param applicantRepo
     */
    @Autowired
    public ApplicantServiceImpl(ApplicantRepo applicantRepo) {
        this.applicantRepo = applicantRepo;
    }

    /**
     * Apply to a particular job posting
     * @param applicant
     * @param jobApplication
     */
    @Override
    public void apply(Applicant applicant, JobApplication jobApplication) {
        applicant.addApplication(jobApplication);
    }

    /**
     * withdraw from it
     * @param id
     */
    @Override
    public void withdraw(Long id) {

    }

    /**
     * Return all applications
     * @param applicant
     * @return
     */
    @Override
    public Collection<JobApplication> getAllApplications(Applicant applicant) {
        return applicant.getApplications();
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
