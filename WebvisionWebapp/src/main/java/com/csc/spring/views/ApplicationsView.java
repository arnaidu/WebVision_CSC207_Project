package com.csc.spring.views;

import com.csc.spring.Application;
import com.csc.spring.models.Document;
import com.csc.spring.models.JobApplication;
import com.csc.spring.models.JobPosting;
import com.csc.spring.service.CoordinatorService;
import com.csc.spring.service.JobApplicationService;
import com.csc.spring.service.JobPostingService;
import com.csc.spring.util.CurrentUser;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.olli.FileDownloadWrapper;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collection;

// This class is to show all the applications for Coordinator and Interviewer
@Route(ApplicationsView.ROUTE)
public class ApplicationsView extends CommonView implements HasUrlParameter<String> {

    public static final String ROUTE = "jobApplications/";

    private Dialog dialogCreateInterview = new Dialog();
    private Dialog dialogHire = new Dialog();
    private VerticalLayout interviewForm = new VerticalLayout();
    private VerticalLayout hireForm = new VerticalLayout();
    private HorizontalLayout buttons = new HorizontalLayout();

    private final CoordinatorService coordinatorService;
    private final JobPostingService jobPostingService;
    private final JobApplicationService jobApplicationService;
    private VerticalLayout grids = new VerticalLayout();
    private Grid<JobApplication> jobApplicationGrid = new Grid<>(JobApplication.class);
    private Grid<Document> documentGrid = new Grid<>(Document.class);
    private JobApplication selectedApp;
    private JobPosting jobPosting;

    /**
     * Creates the Applications View for Coordinator or Interviewer depending on which user is logged in
     * @param jobPostingService
     * @param jobApplicationService
     * @param coordinatorService
     */
    @Autowired
    public ApplicationsView(JobPostingService jobPostingService,
                            JobApplicationService jobApplicationService, CoordinatorService coordinatorService) {
        super();

        this.jobPostingService = jobPostingService;
        this.jobApplicationService = jobApplicationService;
        this.coordinatorService = coordinatorService;
        setUpView();

    }

    public void setUpView() {
        jobApplicationGrid.setColumns("firstName", "lastName", "email", "dateCreated", "applicationStatus");
        jobApplicationGrid.setItems(getApplications());

        grids.add(jobApplicationGrid);

        jobApplicationGrid.asSingleSelect().addValueChangeListener(event -> {
            selectedApp = jobApplicationGrid.asSingleSelect().getValue();
            ArrayList<Document> documents = new ArrayList<>(jobApplicationService.getDocuments(selectedApp));
            ArrayList<String> fileNames = jobApplicationService.getFileNames(selectedApp);
            documentGrid.setColumns("name");
            documentGrid.setItems(documents);
            VerticalLayout downloadButtons = new VerticalLayout();
            for (int i = 0; i < documents.size(); i++) {
                Button downloadButton = new Button("Download " + fileNames.get(i));
                int finalI = i;
                FileDownloadWrapper buttonWrapper = new FileDownloadWrapper(new StreamResource(fileNames.get(i),
                        () -> new ByteArrayInputStream(documents.get(finalI).getContents())));
                buttonWrapper.wrapComponent(downloadButton);
                downloadButtons.add(buttonWrapper);
            }
            grids.add(documentGrid, downloadButtons);
        });

        // Will make according to Coordinator and interviewer
        if (CurrentUser.type.toString().equals("INTERVIEWER")) {

            appLayout.setContent(grids);
            add(appLayout);
        } else {

            addButtons();
            createInterviewForm();
            createHireForm();
            setUpDialogs();
            grids.add(buttons);
            appLayout.setContent(grids);
            add(appLayout);
        }
    }

    // Part of View for Coordinator

    // When click on grid element, both of these should appear

    /**
     * Add Buttons to the view
     */
    private void addButtons(){
        Button createInterview = new Button("Create Interview");
        createInterview.addClickListener(event -> {
            dialogCreateInterview.open();
        });
        buttons.add(createInterview);
        Button hire = new Button("Hire Applicant");
        hire.addClickListener(event -> {
            dialogHire.open();
        });
        buttons.add(hire);
    }

    /**
     * add the ability to create an interview
     */
    private void createInterviewForm(){
        TextField time = new TextField("Time of Interview");
        TextField company = new TextField("Enter Company for Interview");
        TextField email = new TextField("Enter Interviewer Email");
        TextField description = new TextField("Enter description");
        TextField applicantEmail = new TextField("Enter Applicant Email");
        TextField job = new TextField("Enter Job for Interview");
        Button createInterview = new Button("Create Interview", event -> {
            coordinatorService.createInterview(time.getValue(), company.getValue(), email.getValue(),
                    description.getValue(), applicantEmail.getValue(), job.getValue());
            dialogCreateInterview.close();
        });
        interviewForm.add(time, company,email, description, applicantEmail, job, createInterview);
    }

    /**
     * add the ability to hire an applicant
     */
    private void createHireForm(){

        TextField jobName = new TextField("Enter Job Name to Hire");
        TextField applicantEmail = new TextField("Enter applicant email");
        Button hire = new Button("Hire Applicant", event -> {
            coordinatorService.hire(jobName.getValue(), applicantEmail.getValue());
            dialogHire.close();
        });
        hireForm.add(jobName,applicantEmail,hire);

    }

    /**
     * Set up the overlays when buttons are clicked
     */
    private void setUpDialogs(){

        dialogCreateInterview.add(interviewForm);
        dialogCreateInterview.setSizeFull();
        dialogCreateInterview.setCloseOnEsc(false);
        dialogCreateInterview.setCloseOnOutsideClick(false);
        dialogHire.add(hireForm);
        dialogHire.setCloseOnEsc(false);
        dialogHire.setCloseOnOutsideClick(false);
        dialogHire.setSizeFull();


    }

    /**
     * return all applications for specific job_Id
     * @return Collection<JobApplication>
     */
    public Collection<JobApplication> getApplications() {
        System.out.println(jobPosting == null);
        if (jobPosting == null){
            return jobApplicationService.findAll();
        }
        return jobApplicationService.findByJobApplication(jobPosting.getId());
        //return jobApplicationService.findAll(); //jobApplicationService.findByJobApplication(jobPosting.getId());
    }

    /**
     * Gets value of url, the OptionalParameter makes it so that if null, then will route according to ROUTE
     * @param event
     * @param parameter
     */
    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter  String parameter) {
        //Long jobId = Long.valueOf(parameter);
        //jobPosting = jobPostingService.findById(jobId);
        System.out.println(parameter);
        //setUpView();
    }
}
