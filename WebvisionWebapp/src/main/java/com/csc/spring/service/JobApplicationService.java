package com.csc.spring.service;

import com.csc.spring.models.ApplicationStatus;
import com.csc.spring.models.Document;
import com.csc.spring.models.JobApplication;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public interface JobApplicationService {
    /**
     * To be injected as an intermediary between the views and job application repository
     */
    void submit(JobApplication jobApplication, HashMap<String, InputStream> files);

    void withdraw(JobApplication jobApplication);

    void setStatus(JobApplication jobApplication, ApplicationStatus status);

    Collection<JobApplication> findAll();

    Collection<JobApplication> findByJobApplication(Long jobId);

    Collection<Document> getDocuments(JobApplication jobApplication);

    ArrayList<String> getFileTypes(JobApplication jobApplication);

    ArrayList<String> getFileNames(JobApplication jobApplication);

    Collection<JobApplication> findByEmail(String email);
}
