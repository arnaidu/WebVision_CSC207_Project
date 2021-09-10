package com.csc.spring.service;

import com.csc.spring.models.Company;
import com.csc.spring.models.User;

import java.util.Collection;

public interface CompanyService {
    // Company class to be injected and used as intermediary between front-end and database

    Company save(Company company);

    boolean companyExists(String name);

    Company registerCompany(String name, User user);

    void delete(Company company);

    void delete(String name);

    Company addUser(User user, String name);

    void deleteUser(User user, String name);

    Collection<User> getAll(String name);


}
