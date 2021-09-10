package com.csc.spring.service;

import com.csc.spring.models.User;

import java.util.Collection;
import java.util.List;

public interface UserService {

    /**
     * To be injected as an intermediary between the views and User repository
     */

    User findByEmail(String email);

    User save(User user);

    void delete(User user);

    void delete(String email);

    Collection<User> getAll();

    Collection<User> getAll(String role);

    User registerUser(String firstName, String lastName, String email, String password, String role);

}
