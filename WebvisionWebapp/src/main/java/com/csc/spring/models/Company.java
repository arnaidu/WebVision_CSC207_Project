package com.csc.spring.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "company")
public class Company {
     // Data class for company, to be stored in database

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;    // Unique key

    @Column(name = "name")
    private String name;    // Name of the company

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "company_users", joinColumns = @JoinColumn(
                    name = "company_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id")
    )
    private Collection<User> users;     // A collection of all users associated with this company

    public Company() {}

    public Company(String name) { this.name = name; }

    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }

    public void setUsers(Collection<User> users) { this.users = users; }

    public Collection<User> getUsers() { return this.users; }

    public User getUser(User user) { return (users.contains(user)) ? user : null;}

}
