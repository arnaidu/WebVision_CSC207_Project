package com.csc.spring.service;

import com.csc.spring.models.JobPosting;
import com.csc.spring.models.User;

import java.util.Calendar;
import java.util.Collection;

public interface CoordinatorService {
    // To be injected as an itermediary between the front end and the database

    void createJobPosting(String name, String description, String[] requirements, String company);

    void createJobPosting(String name, String description, String[] requirements, String company,
                          int maxApplicants, String skills, Calendar deadline);

    void updateName(String name, Long id);

    void updateMaxApplicants(int num, Long id);

    void updateStatus(int numHired, Long id);

    void updateDescription(String description, Long id);

    void updateRequirements(String[] requirements, Long id);

    User getApplicant(String email);

    Collection<User> getAllApplicants(Long id);

    void deleteJobPosting(Long id);

    void deleteJobPosting(String name);

    void createInterview(String time, String company, String interviewerEmail, String description, String email, String jobName);

    void hire(String jobName, String email);
}
