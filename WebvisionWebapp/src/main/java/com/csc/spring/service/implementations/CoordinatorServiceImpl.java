package com.csc.spring.service.implementations;

import com.csc.spring.models.Interview;
import com.csc.spring.models.JobPosting;
import com.csc.spring.models.User;
import com.csc.spring.observer.MultiNotificationEvent;
import com.csc.spring.observer.NotificationEvent;
import com.csc.spring.service.CoordinatorService;
import com.csc.spring.service.InterviewerService;
import com.csc.spring.service.JobPostingService;
import com.csc.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Collection;

@Service
public class CoordinatorServiceImpl implements CoordinatorService, ApplicationEventPublisherAware {

    /**
     * Implementation of the CoordinatorService to be injected and used as intermediary between
     * database and front-end.
     */



    @Autowired
    private UserService userService;    // Inject User service/controller

    @Autowired
    private JobPostingService postingService;   // Inject JobPosting service/controller

    @Autowired
    private InterviewerService interviewerService; // Inject the interview repo so Coordinator can create an interview

    @Autowired
    private ApplicationEventPublisher publisher; // Inject a publisher to publish notifications



    /**
     * Create a new JobPosting object and save it in the database
     * @param name
     * @param description
     * @param requirements
     * @param company
     */

    @Override
    public void createJobPosting(String name, String description, String[] requirements, String company) {

        JobPosting posting = new JobPosting();
        posting.setName(name);
        posting.setCompany(company);
        posting.setDescription(description);


        postingService.save(posting);
    }

    /**
     * Creates a new job posting object
     * @param name
     * @param description
     * @param requirements
     * @param company
     * @param maxApplicants
     */
    @Override
    public void createJobPosting(String name, String description, String[] requirements, String company,
                                 int maxApplicants, String skills, Calendar deadline) {
        JobPosting posting = new JobPosting(name, description, requirements, company, maxApplicants, skills, deadline);
        postingService.save(posting);
    }


    /**
     * Update name of user object
     * @param name
     * @param id
     */

    @Override
    public void updateName(String name, Long id) {
        JobPosting posting = postingService.findById(id);
        String message = "Name of job " + posting.getName() +" has changed to " + name +".";
        posting.setName(name);
        postingService.save((posting));

        //Add the notification for all applicants

        Collection<User> applicants = getAllApplicants(posting.getId());
        MultiNotificationEvent not = new MultiNotificationEvent(this, message, applicants);
        publisher.publishEvent(not);
    }


    /**
     * Update description of user object
     * @param description
     * @param id
     */

    @Override
    public void updateDescription(String description, Long id) {
        JobPosting posting = postingService.findById(id);
        posting.setDescription(description);
        postingService.save((posting));

        //Add the notification for all applicants
        String message = "Description of job, " +posting.getName() +" has been changed.";
        Collection<User> applicants = getAllApplicants(posting.getId());
        MultiNotificationEvent not = new MultiNotificationEvent(this, message, applicants);
        publisher.publishEvent(not);
    }


    /**
     * Update requirements of user object
     * @param requirements
     * @param id
     */

    @Override
    public void updateRequirements(String[] requirements, Long id) {
        JobPosting posting = postingService.findById(id);
        posting.setRequirements(requirements);
        postingService.save(posting);

        //Add the notification for all applicants
        String message = "Requirements for " + posting.getName() + " has been changed.";
        Collection<User> applicants = getAllApplicants(posting.getId());
        MultiNotificationEvent not = new MultiNotificationEvent(this, message, applicants);
        publisher.publishEvent(not);

    }

    /**
     * update max number of applicants
     * @param maxApplicants
     * @param id
     */
    @Override
    public void updateMaxApplicants(int maxApplicants, Long id){
        JobPosting posting = postingService.findById(id);
        posting.setMaxApplicants(maxApplicants);
        postingService.save(posting);

        //Add the notification for all applicants
        String message = "The max number of applicants for job, " + posting.getName() + " has increased to " +
                maxApplicants + ".";
        Collection<User> applicants = getAllApplicants(posting.getId());
        MultiNotificationEvent not = new MultiNotificationEvent(this, message, applicants);
        publisher.publishEvent(not);
    }

    /**
     * Updates current status
     * @param id
     */
    @Override
    public void updateStatus(int numHired, Long id) {
        JobPosting posting = postingService.findById(id);
        int numAvailable = posting.getNumAvailable() - numHired;
        posting.setNumAvailable(numAvailable);
        postingService.save(posting);

        //Add the notification for all applicants
        String message = "The number of available spots for job, " + posting.getName() + " has been changed to " +
                numAvailable + ".";
        Collection<User> applicants = getAllApplicants(posting.getId());
        MultiNotificationEvent not = new MultiNotificationEvent(this, message, applicants);
        publisher.publishEvent(not);
    }


    /**
     * @param email
     * @return user object with given email
     */

    @Override
    public User getApplicant(String email) {

        return userService.findByEmail(email);
    }


    /**
     * @param id Job Posting id to be retrieved from database
     * @return A collection of users that are currently applying to this job posting
     */

    @Override
    public Collection<User> getAllApplicants(Long id) {
        JobPosting posting = postingService.findById(id);
        return posting.getUsers();


    }


    /**
     * Delete JobPosting object
     * @param name
     */

    @Override
    public void deleteJobPosting(String name) {
        JobPosting posting = postingService.findByName(name);
        postingService.delete(name);

        //Add the notification for all applicants
        String message = "Job " + posting.getName() + " has been deleted.";
        Collection<User> applicants = getAllApplicants(posting.getId());
        MultiNotificationEvent not = new MultiNotificationEvent(this, message, applicants);
        publisher.publishEvent(not);

    }


    /**
     * Delete JobPosting object
     * @param id
     */

    @Override
    public void deleteJobPosting(Long id) {
        JobPosting posting = postingService.findById(id);
        postingService.delete(id);

        //Add the notification for all applicants
        String message = "Job " + posting.getName() + " has been deleted.";
        Collection<User> applicants = getAllApplicants(posting.getId());
        MultiNotificationEvent not = new MultiNotificationEvent(this, message, applicants);
        publisher.publishEvent(not);
    }

    /**
     * Creates a new interview object
     * @param time
     * @param company
     * @param interviewerEmail
     * @param description
     * @param email
     * @param jobName
     */
    @Override
    public void createInterview(String time, String company, String interviewerEmail, String description, String email, String jobName){

        Interview interview = new Interview(time, company, interviewerEmail, description, email, jobName);
        interviewerService.save(interview);

        // Send notification to single interviewer
        String message = "You have a new interview for job: " + jobName +".";
        User user = userService.findByEmail(interviewerEmail);
        NotificationEvent not = new NotificationEvent(this, message, user.getId());
        publisher.publishEvent(not);

    }

    /**
     * Allows coordinator to hire an applicant
     * @param jobName
     * @param email
     */
    @Override
    public void hire(String jobName, String email){

        // This will reduce the number of positions available by 1
        // Add a precondition saying that max is greater than or equal to 1
        JobPosting posting = postingService.findByName(jobName);
        User applicant = userService.findByEmail(email);
        Long toUserId = applicant.getId();
        int maxApplicants = posting.getMaxApplicants();
        if (maxApplicants == 1) {
            updateStatus(1, posting.getId());
        }

        //Add the notification for an applicant who is hired
        String message = "You have been hired for job: " + posting.getName() +".";
        NotificationEvent not = new NotificationEvent(this, message, toUserId);
        publisher.publishEvent(not);

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


