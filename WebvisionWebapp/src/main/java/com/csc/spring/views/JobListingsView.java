package com.csc.spring.views;

import com.csc.spring.db.JobPostingRepo;
import com.csc.spring.models.JobPosting;
import com.csc.spring.service.JobPostingService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;

@Route(JobListingsView.ROUTE)
public class JobListingsView extends CommonView implements HasUrlParameter<String> {
    public static final String ROUTE = "jobs";

    private final JobPostingService jobPostingService;

    private TextField filterField = new TextField();
    private Checkbox searchSkills = new Checkbox("Skills Search");

    private ArrayList<JobCard> cards = new ArrayList<>();
    private VerticalLayout cardsLayout;
    private ArrayList<JobPosting> jobPostings = new ArrayList<>();
    private ArrayList<JobPosting> jobPostingsVisible = new ArrayList<>();

    private String urlParameter;

    private boolean generateTestData = true;

    /**
     * Create the view to see all jobListings -- this is for applicant
     */
    @Autowired
    public JobListingsView(JobPostingService jobPostingService) {
        super();
        super.refreshView();
        this.jobPostingService = jobPostingService;

        if (generateTestData) {
            generateData();
        }
    }

    public void showJobs() {
        if (urlParameter == null || urlParameter.equals("applicant")) {
            System.out.println("urlParameter");
            System.out.println(urlParameter);
            setJobPostingsFull();
        } else {
            System.out.println("bottom");
            setJobPostingSubset();
        }

        filterField.setPlaceholder("Search Jobs...");
        filterField.setValueChangeMode(ValueChangeMode.EAGER);
        filterField.addKeyPressListener(Key.ENTER, event ->
        {
            String filter = filterField.getValue();
            showCards(filter);
        });
        add(appLayout);
        cardsLayout = new VerticalLayout(filterField, searchSkills);
        appLayout.setContent(cardsLayout);
        showCards(null);
    }

    /**
     * Does the filtering of job cards
     * If the searchSkills checkbox is checked, it will search by the skills of the job instead
     *
     * @param filter the text user enters in the text field
     */
    public void showCards(String filter) {

        appLayout.removeContent();
        jobPostingsVisible.clear();
        cards.clear();

        if (filter == null) {
            jobPostingsVisible.addAll(jobPostings);
        } else if (!searchSkills.getValue()) {
            for (JobPosting posting : jobPostings) {
                boolean pass = posting.getName().toLowerCase().contains(filter.trim().toLowerCase());
                if (pass) {
                    jobPostingsVisible.add(posting);
                }
            }
        } else {
            for (JobPosting posting : jobPostings) {
                boolean pass = posting.getSkills().toLowerCase().contains(filter.trim().toLowerCase());
                if (pass) {
                    jobPostingsVisible.add(posting);
                }
            }
        }
        for (JobPosting posting : jobPostingsVisible) {
            cards.add(new JobCard("frontend/benzene.jpg", posting, urlParameter));
        }
        cardsLayout = new VerticalLayout(filterField);
        for (JobCard card : cards) {
            cardsLayout.add(card);
        }
        appLayout.setContent(cardsLayout);
    }

    /**
     * Subsets the visible job postings for a coordinator
     */
    public void setJobPostingSubset() {

        jobPostings = new ArrayList<>(jobPostingService.findByCompany(urlParameter));
//        jobPostings = new ArrayList<>(jobPostingService.getAll());
    }

    /**
     * Shows all available job postings for an applicant
     */
    public void setJobPostingsFull() {
        jobPostings = new ArrayList<>(jobPostingService.getAll());
    }


    public void generateData() {
        jobPostingService.generateTestData();
    }

    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        this.urlParameter = parameter;
        super.refreshView();
        showJobs();
    }
}
