package com.csc.spring.db;

import com.csc.spring.models.Interview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface InterviewRepo extends JpaRepository<Interview, Long> {
    // Database model for interviews

    Interview findByTime(String time);

    Interview findByCompany(String company);

    Interview findByInterviewerEmail(String email);

    List<Interview> getAllByCompany(String company);

    List<Interview> getAllByInterviewerEmail(String email);

}
