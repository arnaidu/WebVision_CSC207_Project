package com.csc.spring.service;

import com.csc.spring.models.JobPosting;

import java.util.Collection;


public interface JobPostingService {

    /**
     * To be injected as an intermediary between the views and job posting repository
     */

    void save(JobPosting jobPosting);

    void delete(JobPosting jobPosting);

    void delete(String name);

    void delete(Long id);

    JobPosting findByName(String name);

    JobPosting findById(Long id);

    Collection<JobPosting> getAll();

    void generateTestData();

    Collection<JobPosting> findByCompany(String name);
}
