package com.csc.spring.service;

import com.csc.spring.models.Interview;
import com.csc.spring.models.User;

import java.util.List;

public interface InterviewerService {

    /**
     * To be injected as an intermediary between the views and interview repository
     */

    List<Interview> getInterviews(String interviewerEmail);

    void recommend(String message, Long toUserId);

    void reject(Interview interview, String message, Long toUserId);

    String getDescription(Interview interview);

    void save(Interview interview);



}
