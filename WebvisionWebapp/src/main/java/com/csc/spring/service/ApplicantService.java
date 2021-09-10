package com.csc.spring.service;

import com.csc.spring.models.Applicant;
import com.csc.spring.models.JobApplication;

import java.util.Collection;

public interface ApplicantService {

    /**
     * To be injected as an intermediary between the views and applicant repository
     */

    void apply(Applicant applicant, JobApplication jobApplication);

    void withdraw(Long id);

    Collection<JobApplication> getAllApplications(Applicant applicant);

}
