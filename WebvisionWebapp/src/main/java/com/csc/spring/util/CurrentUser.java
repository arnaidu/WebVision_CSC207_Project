package com.csc.spring.util;

import com.csc.spring.db.ApplicantRepo;
import com.csc.spring.db.CompanyRepo;
import com.csc.spring.models.Applicant;
import com.csc.spring.models.Company;
import com.csc.spring.models.User;
import com.csc.spring.models.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class CurrentUser {
    /**
     * To be used as a state class, using the benefit of Singleton pattern by java spring
     *  and using the advantage of the context
     */

    public static User currentUser;

    public static Applicant currentApplicant;
    public static ApplicantRepo applicantRepo;
    public static CompanyRepo companyRepo;

    public static UserType type;

    public static Company company;

    /**
     * Init to null
     * @param applicantRepo
     */
    @Autowired
    CurrentUser(ApplicantRepo applicantRepo, CompanyRepo companyRepo) {
        CurrentUser.applicantRepo = applicantRepo;
        CurrentUser.companyRepo = companyRepo;
        currentUser = null;
        type = null;
    }

    /**
     * Set current user agter authentication or registration
     * @param user
     */
    public static void setCurrentUser(User user) {
        currentUser = user;
        if (UserType.getType(user.getRole()) == UserType.APPLICANT) {
            currentApplicant = applicantRepo.findByUserId(user.getId());
        }
        type = UserType.getType(user.getRole());
        company = companyRepo.findCompanyByUsers(user);
    }

    /**
     * Set user type
     * @param newType
     */
    public static void setNewUserType(UserType newType) {
        type = newType;
        if (type == UserType.APPLICANT) {
            currentApplicant = new Applicant();
            currentApplicant.setUserId(currentUser.getId());
            applicantRepo.save(currentApplicant);
//            currentApplicant = applicantRepo.findByUserId(currentUser.getId());
        }
    }

    // Getters and setters
    public static void resetCurrentUser() {
        currentUser = null;
        currentApplicant = null;
    }

    public static void resetUserType() { type = null; }

    public static void setCompany(Company newCompany) { company = newCompany; }

    public static Long getCurrentUserId() { return (currentUser == null ? null : currentUser.getId()); }

    public static String getFirstName() { return (currentUser == null ? null : currentUser.getFirstName()); }

    public static String getLastName() { return (currentUser == null ? null : currentUser.getLastName()); }

    public static String getEmail() { return (currentUser == null ? null : currentUser.getEmail()); }

    public static String getCompany() { return (company == null ? null : company.getName()); }

    public static boolean isEmpty() { return currentUser == null; }

    public static void resetCompany() { company = null; }
}
