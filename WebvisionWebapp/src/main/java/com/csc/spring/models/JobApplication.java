package com.csc.spring.models;

import org.apache.commons.lang3.ArrayUtils;

import javax.persistence.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "job_application")
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "job_application_id")
    private Long id;

    @Column(name = "target_job_id")
    private Long jobId;

    @Column(name = "job_name")
    private String jobName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "date_created")
    private Date dateCreated;

    @Column(name = "application_status")
    private ApplicationStatus applicationStatus;

    // These fetch and cascade properties allow child objects to be saved to database automatically when
    // an instance of this object is saved
    // Used when the collection of documents need to be saved to DocumentRepo, but the current modified Applicant
    // model needs to be saved to ApplicantRepo as well
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "application_documents",
//            joinColumns = @JoinColumn(name = "job_application_id", referencedColumnName = "job_application_id"),
//            inverseJoinColumns = @JoinColumn(name = "document_id", referencedColumnName = "document_id")
//    )
    private Collection<Document> documents;

    public void setDocuments(Collection<Document> documents) {
        this.documents = documents;
    }

    public Collection<Document> getDocuments() {
        return this.documents;
    }

    /**
     * Create a jobApplication object
     */
    public JobApplication() {
        this.dateCreated = new Date();
        this.applicationStatus = ApplicationStatus.Submitted;
    }

    /* Getter and Setters */
    public Long getId() {
        return id;
    }
    public Long getJobId() {
        return jobId;
    }
    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
    public ApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }
    public void setApplicationStatus(ApplicationStatus applicationStatus) {
        this.applicationStatus = applicationStatus;
    }
    public Date getDateCreated() {
        return dateCreated;
    }
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
