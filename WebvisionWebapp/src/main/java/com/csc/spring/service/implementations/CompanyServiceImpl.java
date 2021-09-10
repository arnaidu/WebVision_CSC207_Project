package com.csc.spring.service.implementations;

import com.csc.spring.db.CompanyRepo;
import com.csc.spring.models.Company;
import com.csc.spring.models.User;
import com.csc.spring.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class CompanyServiceImpl implements CompanyService {
    /**
     * To be injected as an intermediary between the front-end and database.
     */
    @Autowired
    private CompanyRepo repo;   // Database controller

    /**
     * @param company
     * @return saved Company in database.
     */
    @Override
    public Company save(Company company) {
        return repo.save(company);
    }

    /**
     * Delete Company Object.
     * @param company
     */
    @Override
    public void delete(Company company) {
        repo.delete(company);
    }

    /**
     * Delete Company object.
     * @param company
     */
    @Override
    public void delete(String company) {
        Company toDelete = repo.findByName(company);

        if (toDelete == null) return;

        repo.delete(toDelete);
    }

    /**
     * Add user to list of users related to this company.
     * @param user
     * @param name Company name
     */
    @Override
    public Company addUser(User user, String name) {
        Company company = repo.findByName(name);

        if (company == null) {
            return null;
        }

        ArrayList<User> users = new ArrayList<>(company.getUsers());
        users.add(user);
        company.setUsers(users);

        return company;
    }

    /**
     * Deletes a user from company user related list.
     * @param user
     * @param name Company name
     */
    @Override
    public void deleteUser(User user, String name) {
        Company company = repo.findByName(name);

        if (company == null) return;

        ArrayList<User> users = new ArrayList<>(company.getUsers());
        users.remove(user);
        company.setUsers(users);
    }

    /**
     * @param name Company name.
     * @return A list of users related to this company.
     */
    @Override
    public Collection<User> getAll(String name) {
        Company company = repo.findByName(name);

        if (company == null) return null;

        return company.getUsers();
    }

    @Override
    public boolean companyExists(String name) {
        Company company = repo.findByName(name);

        return company != null;
    }

    /**
     * @param name
     * @param user
     * @return Newly created company instance
     */
    @Override
    public Company registerCompany(String name, User user) {
        //ArrayList<User> users = new ArrayList<>();
        Company company = new Company();

        company.setName(name);
        //company.addUsers(user);
        //company.setUsers(users);

        repo.save(company);

        addUser(user, name);
        return company;
    }
}
