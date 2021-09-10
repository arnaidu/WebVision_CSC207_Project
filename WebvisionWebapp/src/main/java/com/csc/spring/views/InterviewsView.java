package com.csc.spring.views;

import com.csc.spring.models.ApplicationStatus;
import com.csc.spring.models.Interview;
import com.csc.spring.models.JobApplication;
import com.csc.spring.models.User;
import com.csc.spring.service.InterviewerService;
import com.csc.spring.service.JobApplicationService;
import com.csc.spring.service.JobPostingService;
import com.csc.spring.service.UserService;
import com.csc.spring.util.CurrentUser;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;


@Route(InterviewsView.ROUTE)
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class InterviewsView extends CommonView {

    private Grid<Interview> interviews = new Grid<>(Interview.class);
    private HorizontalLayout buttons = new HorizontalLayout();
    private VerticalLayout layout = new VerticalLayout();
    private VerticalLayout recommendForm = new VerticalLayout();
    private VerticalLayout rejectForm = new VerticalLayout();
    private Dialog dialogRecommend = new Dialog();
    private Dialog dialogReject = new Dialog();
    private Interview selected; //This is to get a selected element from the grid when clicked

    public static final String ROUTE = "interviews";

    private InterviewerService interviewerService; //Inject InterviewerService to get functionality

    private JobApplicationService jobApplicationService; // Inject JobApplicationService to change status of them

    private UserService userService; //Inject UserService to handle users not explicitly given

    private JobPostingService jobPostingService;

    /**
     * Create the view to see all interviews for an interviewer and add to the overall layout (common view)
     * @param interviewerService
     * @param jobApplicationService
     * @param userService
     * @param jobPostingService
     */
    @Autowired
    public InterviewsView(InterviewerService interviewerService, JobApplicationService jobApplicationService,
                          UserService userService, JobPostingService jobPostingService){
        super();

        this.interviewerService = interviewerService;
        this.userService = userService;
        this.jobApplicationService = jobApplicationService;
        this.jobPostingService = jobPostingService;

        createGrid();
        addRecommendRejectButton();
        createRecommendForm();
        createRejectForm();
        setUpDialogs();
        layout.add(buttons);
        appLayout.setContent(layout);
        add(appLayout);

    }

    /* All Methods below are helper methods to help build the view bit by bit */

    private void createGrid(){

        //retrieve the list of interviews from the interviewerService
        List<Interview> list = interviewerService.getInterviews(CurrentUser.getEmail());
        interviews.setItems(list); //put the list of interviews in here

        interviews.setColumns("time", "company", "fromCoordinator", "applicantEmail", "jobPosting");
        interviews.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        layout.add(interviews);

        // the description includes the applicant name --> add the view application thing like coordinator to interviewer
        interviews.asSingleSelect().addValueChangeListener(event ->  {
            selected = interviews.asSingleSelect().getValue();
            Label temp = new Label(interviewerService.getDescription(selected));
            temp.setSizeFull();
            buttons.add(temp);
            buttons.setVisible(true);
        });

    }

    private void addRecommendRejectButton(){

        Button recommend = new Button("Recommend Applicant");
        recommend.addClickListener(event -> {
           dialogRecommend.open();
        });
        Button reject = new Button("Reject Applicant");
        reject.addClickListener(event -> {
            dialogReject.open();
        });
        buttons.add(recommend, reject);
        buttons.setVisible(false);

    }

    private void setUpDialogs(){

        dialogRecommend.add(recommendForm);
        dialogRecommend.setSizeFull();
        dialogRecommend.setCloseOnEsc(false);
        dialogRecommend.setCloseOnOutsideClick(false);
        dialogReject.add(rejectForm);
        dialogReject.setCloseOnEsc(false);
        dialogReject.setSizeFull();
        dialogReject.setCloseOnOutsideClick(false);


    }

    private void createRecommendForm(){
        TextField applicantEmail = new TextField("Enter Applicant Email");
        TextField newType = new TextField("Recommend Interview Type");
        TextField jobName = new TextField("Enter Job Name");
        Label instruction = new Label("Enter any other relevant information here:");
        TextField other = new TextField("Other");

        Button recommend = new Button("Recommend", event -> {
            // Add a notification to show user task is completed
            User user = userService.findByEmail(applicantEmail.getValue());
            String message = CurrentUser.getFirstName() + " " + CurrentUser.getLastName() + " with email " +
                    CurrentUser.getEmail() +" has recommended " + user.getFirstName() + " " + user.getLastName()
                    + "with email " + user.getEmail() + " to be able to get an interview of type " +
                    newType.getValue() +"."+ " Other relevant information: " + other.getValue() +".";

            // Carry out the task --> this will also send the notification to coordinator who set up interview
            interviewerService.recommend(message, selected.getFromCoordinator());
            Notification.show("Successfully recommended " + user.getFirstName()+ " " + user.getLastName());

            // Update status of application
            Collection<JobApplication> apps = jobApplicationService.findByEmail(applicantEmail.getValue());
            Long jobId = jobPostingService.findByName(jobName.getValue()).getId();
            updateStatusApplication(apps, ApplicationStatus.InterviewRequested, jobId);
            dialogRecommend.close();
        });
        recommendForm.add(applicantEmail, newType, instruction, other, recommend);
    }


    private void createRejectForm(){

        TextField applicantEmail = new TextField("Enter Applicant Email");
        TextField jobName = new TextField("Enter Job Name");
        Button reject = new Button("Reject", event -> {
            User user = userService.findByEmail(applicantEmail.getValue());
            String message = "You have been rejected from your application for " + selected.getJobPosting() +"." +
                    " If you have any questions, contact us at " + CurrentUser.getEmail() +".";
            interviewerService.reject(selected, message, user.getId());
            Notification.show("Successfully rejected " + user.getFirstName() + " " +
                    user.getLastName() + " " + user.getEmail());
            // --> Change application status here (how to do this????)
            Collection<JobApplication> apps = jobApplicationService.findByEmail(applicantEmail.getValue());
            Long jobId = jobPostingService.findByName(jobName.getValue()).getId();
            updateStatusApplication(apps, ApplicationStatus.Rejected, jobId);
            dialogRecommend.close();
        });
        recommendForm.add(jobName, applicantEmail, reject);

    }

    private void updateStatusApplication(Collection<JobApplication> applications, ApplicationStatus status, Long id){

        for (JobApplication app: applications){
            if (app.getJobId().equals(id)){
                app.setApplicationStatus(status);
            }
        }

    }


}
