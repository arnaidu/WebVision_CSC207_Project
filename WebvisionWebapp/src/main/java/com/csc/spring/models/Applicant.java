package com.csc.spring.models;

import com.csc.spring.db.UserRepo;
import com.csc.spring.util.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "applicant")
public class Applicant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "applicant_id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    // These fetch and cascade properties allow child objects to be saved to database automatically when
    // an instance of this object is saved
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "applicant_applications",
            joinColumns = @JoinColumn(name = "applicant_id", referencedColumnName = "applicant_id"),
            inverseJoinColumns = @JoinColumn(name = "job_application_id", referencedColumnName = "job_application_id")
    )
    private Collection<JobApplication> applications;

    public Applicant() {
        this.applications = new ArrayList<>();
    }


    /* Getter and Setters */
    public void setUserId(Long id) { this.userId = id; }
    public void setId(Long id) { this.id = id; }
    public void setApplications(Collection<JobApplication> applications) { this.applications = applications; }
    public Long getId() { return this.id; }
    public Long getUserId() { return this.userId; }
    public Collection<JobApplication> getApplications() { return this.applications; }

    /* Methods to add or remove an application */
    public void addApplication(JobApplication application) {
        this.applications.add(application);
    }

    public void removeApplication(JobApplication application) {
        this.applications.remove(application);
    }



}
