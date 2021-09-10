package com.csc.spring.models;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "job_posting")
public class JobPosting {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "posting_id")
    private Long postingId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    @Length(min = 0, max = 10000)
    private String description;

    @Column(name = "requirements")
    private String[] requirements;

    @Column(name = "skills")
    private String skills;

    @Column(name = "company")
    private String company;

    @Column(name = "num_available")
    private int numAvailable;

    @Column(name = "deadline")
    private Calendar deadline;

    private int maxApplicants; // This puts a limit on number of applicants for a job posting

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "posting_users",
            joinColumns = @JoinColumn(name = "job_posting_id", referencedColumnName = "posting_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
            )
    private Collection<User> users;

    public JobPosting() {}

    /**
     * Constructor to make a minimal job posting
     * @param name
     * @param description
     * @param requirements
     * @param company
     */
    public JobPosting(String name, String description, String[] requirements, String company) {
        this.name = name;
        this.description = description;
        this.requirements = requirements;
        this.company = company;
    }

    /**
     * Constructor to make a more complete job posting than above
     * @param name
     * @param description
     * @param requirements
     * @param company
     * @param maxApplicants
     * @param skills
     * @param deadline
     */
    public JobPosting(String name, String description, String[] requirements,
                      String company, int maxApplicants, String skills, Calendar deadline) {
        this.name = name;
        this.description = description;
        this.requirements = requirements;
        this.company = company;
        this.maxApplicants = maxApplicants;
        this.skills = skills;
        this.deadline = deadline;
        this.numAvailable = maxApplicants;
    }

    /* Getters  */

    public String getName() { return this.name; }
    public String getDescription() { return this.description; }
    public String getCompany() {
        return company;
    }
    public Collection<User> getUsers() { return this.users; }
    public Long getId() {
        return postingId;
    }
    public int getMaxApplicants() {
        return maxApplicants;
    }

    /* Setters */
    public void setName(String name) { this.name = name; }
    public  void setDescription(String description) { this.description = description; }
    public void setMaxApplicants(int maxApplicants) {
        this.maxApplicants = maxApplicants;
    }
    public void setUsers(Collection<User> users) { this.users = users; }
    public void setCompany(String company) {
        this.company = company;
    }
    public void setRequirements(String[] requirements) {
        this.requirements = requirements;
    }

    public int getNumAvailable() {
        return numAvailable;
    }

    public void setNumAvailable(int numAvailable) {
        this.numAvailable = numAvailable;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public Date getDeadline() {
        return deadline.getTime();
    }

    public void setDeadline(Calendar deadline) {
        this.deadline = deadline;
    }
}
