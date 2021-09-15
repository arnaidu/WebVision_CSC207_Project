package com.csc.spring.db;

import com.csc.spring.models.Company;
import com.csc.spring.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CompanyRepo extends JpaRepository<Company, Long> {
    // Company database model

    Company findByName(String name);
    Company findCompanyByUsers(User user);
}
