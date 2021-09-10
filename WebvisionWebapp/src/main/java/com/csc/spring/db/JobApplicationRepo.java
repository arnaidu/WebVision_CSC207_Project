package com.csc.spring.db;

import com.csc.spring.models.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface JobApplicationRepo extends JpaRepository<JobApplication, Long> {
    // Job application database model
    Collection<JobApplication> findAllByJobId(Long jobId);

    Collection<JobApplication> findAllByEmail(String email);
}
