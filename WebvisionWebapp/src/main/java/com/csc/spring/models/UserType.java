package com.csc.spring.models;

public enum UserType {
    // To be hold by CurrentUser tells the application the type of user logged in
    COORDINATOR,

    INTERVIEWER,

    APPLICANT;

    public static UserType getType(String type) {
        /**
         * Given the string from the form inside the registration return
         * the UserType it represents
         * @param type From registration view.
         * @return UserType corresponding to string
         */

        switch (type){

            case "APPLICANT":
                return APPLICANT;

            case "INTERVIEWER":
                return INTERVIEWER;

            case "COORDINATOR":
                return COORDINATOR;

            default:
                return null;
        }
    }
}
