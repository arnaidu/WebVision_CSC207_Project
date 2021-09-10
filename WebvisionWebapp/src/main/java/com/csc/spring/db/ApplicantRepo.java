package com.csc.spring.db;

import com.csc.spring.models.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicantRepo extends JpaRepository<Applicant, Long> {
    // Database model for Applicants

    Applicant findByUserId(Long userId);
}
