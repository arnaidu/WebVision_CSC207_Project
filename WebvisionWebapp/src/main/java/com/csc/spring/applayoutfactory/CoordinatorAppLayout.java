package com.csc.spring.applayoutfactory;

import com.csc.spring.util.CurrentUser;
import com.csc.spring.views.JobListingsView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.AppLayoutMenu;
import com.vaadin.flow.component.applayout.AppLayoutMenuItem;
import com.vaadin.flow.component.html.Image;

public class CoordinatorAppLayout implements WebLayout {

    @Override
    public AppLayout createAppLayout() {

        AppLayout appLayout = new AppLayout();
        Image img = new Image("", "Webvision");
        img.setHeight("50px");
        appLayout.setBranding(img);

        // Create Navigation Bar
        AppLayoutMenu menu = appLayout.createMenu();
        AppLayoutMenuItem home = new AppLayoutMenuItem("Home", "home");
        String companyName = CurrentUser.getCompany();
        CurrentUser.currentUser.setRole("COORDINATOR");
        AppLayoutMenuItem jobPosting = new AppLayoutMenuItem("Company Postings",
                JobListingsView.ROUTE + "/" + companyName);
        AppLayoutMenuItem newPosting = new AppLayoutMenuItem("Create New Posting", "jobeditor/new");
        AppLayoutMenuItem applications = new AppLayoutMenuItem("View Applications", "jobApplications/");
        AppLayoutMenuItem logout = new AppLayoutMenuItem("Logout", "");

        menu.addMenuItems(home, jobPosting, newPosting, applications, logout);
        logout.addMenuItemClickListener(event -> {
            CurrentUser.resetCurrentUser();
            CurrentUser.resetUserType();
            CurrentUser.resetCompany();
        });
        return appLayout;
    }
}
