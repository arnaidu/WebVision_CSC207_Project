package com.csc.spring.applayoutfactory;

import com.vaadin.flow.component.applayout.AppLayout;

public class AppLayoutFactory {

    public AppLayout getLayout(String userType){

        if (userType == null){
            return new AppLayout();
        }
        else if (userType.equals("APPLICANT")){
            return new ApplicantAppLayout().createAppLayout();
        }
        else if (userType.equals("COORDINATOR")){
            return new CoordinatorAppLayout().createAppLayout();
        }
        else if (userType.equals("INTERVIEWER")){
            return new InterviewerAppLayout().createAppLayout();
        }
        return null;
    }

}
