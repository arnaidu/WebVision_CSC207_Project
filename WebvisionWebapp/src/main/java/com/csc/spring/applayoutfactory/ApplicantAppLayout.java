package com.csc.spring.applayoutfactory;

import com.csc.spring.util.CurrentUser;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.AppLayoutMenu;
import com.vaadin.flow.component.applayout.AppLayoutMenuItem;
import com.vaadin.flow.component.applayout.MenuItemClickEvent;
import com.vaadin.flow.component.html.Image;

public class ApplicantAppLayout implements WebLayout {

    @Override
    public AppLayout createAppLayout() {

        AppLayout appLayout = new AppLayout();
        Image img = new Image("", "Webvision");
        img.setHeight("50px");
        appLayout.setBranding(img);

        // Create Navigation Bar
        AppLayoutMenu menu = appLayout.createMenu();
        AppLayoutMenuItem home = new AppLayoutMenuItem("Home", "home");
        AppLayoutMenuItem jobPosting = new AppLayoutMenuItem("Job Postings", "jobs/applicant");
        AppLayoutMenuItem history = new AppLayoutMenuItem("Application History", "applications");
        AppLayoutMenuItem logout = new AppLayoutMenuItem("Logout", "");

        logout.addMenuItemClickListener(event -> {
            CurrentUser.resetCurrentUser();
            CurrentUser.resetUserType();
            CurrentUser.resetCompany();
        });
        menu.addMenuItems(home, jobPosting, history, logout);

        return appLayout;
    }
}
