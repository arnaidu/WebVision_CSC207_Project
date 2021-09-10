package com.csc.spring.db;

import com.csc.spring.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo  extends JpaRepository<User, Long> {
    // User database model

    User findByEmail(String email);

    List<User> findByRole(String role);

}
