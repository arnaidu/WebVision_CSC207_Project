package com.csc.spring.models;

import com.csc.spring.util.CurrentUser;

import javax.persistence.*;

@Entity
@Table(name = "interview")
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "interview_id")
    private Long id;

    @Column(name = "time")
    private String time;

    @Column(name = "company")
    private String company;

    @Column(name = "interviewer")
    private String interviewerEmail;

    @Column(name = "description")
    private String description;

    @Column(name = "coordinator_id")
    private Long fromCoordinator;

    @Column(name = "applicant_email")
    private String applicantEmail;

    @Column(name = "posting_job")
    private String jobPosting;

    public Interview() {}

    /**
     * Create an interview object
     * @param time
     * @param company
     * @param interviewerEmail
     * @param description
     * @param applicantEmail
     * @param job
     */
    public Interview(String time, String company, String interviewerEmail, String description,
                     String applicantEmail, String job) {
        this.time = time;
        this.company = company;
        this.interviewerEmail = interviewerEmail;
        this.description = description;

        //This is to keep track of coordinatorId, this way we can send a notification back to the coordinator
        this.fromCoordinator = CurrentUser.getCurrentUserId();

        //This is to keep track of applicant for the interview
        this.applicantEmail = applicantEmail;

        //This is to keep track of the job the interview is for
        this.jobPosting = job;

    }

    /* Getters */
    public String getTime() { return this.time; }
    public String getCompany() { return this.company; }
    public String getInterviewerEmail() { return this.interviewerEmail; }
    public String getDescription() {
        return description;
    }
    public Long getFromCoordinator() {
        return fromCoordinator;
    }
    public String getApplicantEmail() {
        return applicantEmail;
    }
    public String getJobPosting() {
        return jobPosting;
    }

    /* Setters */
    public void setTime(String time) { this.time = time; }
    public void setCompany(String company) { this.company = company; }
    public void setInterviewerEmail(String interviewerEmail) { this.interviewerEmail = interviewerEmail; }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setFromCoordinator(Long fromCoordinator) {
        this.fromCoordinator = fromCoordinator;
    }
    public void setApplicantEmail(String applicantEmail) {
        this.applicantEmail = applicantEmail;
    }
    public void setJobPosting(String jobPosting) {
        this.jobPosting = jobPosting;
    }
}

