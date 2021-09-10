package com.csc.spring.service.implementations;

import com.csc.spring.db.UserRepo;
import com.csc.spring.models.User;
import com.csc.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserServiceImpl implements UserService {
    /**
     * Implementation of UserService this class will provide with  methgds to
     * interact with the user database.
     */

    @Autowired
    UserRepo userRepo;  // Inject the database controller.

    /**
     * @param email
     * @return The saved user
     */
    @Override
    public User findByEmail(String email) {
        User user = userRepo.findByEmail(email);

        if (user == null) {
            return null;
        }

        return user;
    }

    /**
     * @param user
     * @return The saved user
     */
    @Override
    public User save(User user) {
        return userRepo.save(user);
    }

    /**
     * Delete user object
     * @param user
     */
    @Override
    public void delete(User user){
        userRepo.delete(user);
    }

    /**
     * Delete user object
     * @param email
     */
    @Override
    public void delete(String email) {

        User user = userRepo.findByEmail(email);

        if (user == null) return;   // If the user doesn't exist

        userRepo.delete(user);
    }

    /**
     * @return collection of all user objects in database
     */
    @Override
    public Collection<User> getAll() {
        return userRepo.findAll();
    }

    @Override
    public Collection<User> getAll(String role) {return userRepo.findByRole(role);}


    /**
     * Create a new user and save it in the database
     * @param firstName
     * @param lastName
     * @param email
     * @param password
     * @param role
     * @return
     */
    @Override
    public User registerUser(String firstName, String lastName, String email, String password, String role){

        User user =  new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setRole(role);
        user.setPassword(password);

        return save(user);
    }

}
