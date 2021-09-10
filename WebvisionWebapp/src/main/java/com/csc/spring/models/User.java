package com.csc.spring.models;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {
    /**
     * Class User acts as a model to store user information in the database,
     * following the implmentation of JPA repositories.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;    // To be user as key in the database

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;    // APPLICANT, INTERVIEWER, COORDINATOR

    public User() {}

    /**
     * Creates a User object keyed by an id as well as email since that is unique
     * @param firstName
     * @param lastName
     * @param email
     * @param password
     * @param role
     */
    public User(String firstName, String lastName, String email, String password, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    /* Getters */
    public String getFirstName() { return this.firstName; }
    public String getLastName() { return this.lastName; }
    public String getEmail() { return this.email; }
    public String getPassword() { return this.password; }
    public String getRole() { return this.role; }
    public Long getId() { return this.id; }

    /* Setters */
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(String role) { this.role = role; }
    public void setId(Long id) { this.id = id; }
}
