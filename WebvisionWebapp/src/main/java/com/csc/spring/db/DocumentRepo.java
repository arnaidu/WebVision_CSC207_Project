package com.csc.spring.db;

import com.csc.spring.models.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepo extends JpaRepository<Document, Long> {
    // database model for duocuments
}
