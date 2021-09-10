package com.csc.spring.db;

import com.csc.spring.models.JobPosting;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;


@Repository
public interface JobPostingRepo extends JpaRepository<JobPosting, Long> {
    // Job posting database model

    JobPosting findByName(String name);

    Collection<JobPosting> findAllByCompany(String name);

}
