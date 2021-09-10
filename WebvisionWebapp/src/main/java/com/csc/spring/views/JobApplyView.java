package com.csc.spring.views;

import com.csc.spring.models.JobApplication;
import com.csc.spring.models.User;
import com.csc.spring.service.JobApplicationService;
import com.csc.spring.util.CurrentUser;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;
import java.util.HashMap;

@Route(JobApplyView.ROUTE)
public class JobApplyView extends CommonView implements HasUrlParameter<String> {

    public static final String ROUTE = "apply";
    private Long targetJobId;

    private TextField firstName = new TextField("First Name");
    private TextField lastName = new TextField("Last Name");
    private TextField email = new TextField("Email");
    Binder<JobApplication> binder = new Binder<>(JobApplication.class);
    Binder<User> userBinder = new Binder<>();

    boolean uploadCompleted = false;
    HashMap<String, InputStream> inputStreamHashMap = new HashMap<>();

    private Button submitButton = new Button("Submit");

    private VerticalLayout layout = new VerticalLayout();

    @Autowired
    private JobApplicationService jobApplicationService;

    /**
     * Create the apply view for a job posting for applicants to see
     * Binder binds the instance fields of this class to those with the same name in JobApplication model
     */
    public JobApplyView() {
        super();

        userBinder.setBean(CurrentUser.currentUser);
        binder.setBean(new JobApplication());
        submitButton.addClickListener(event -> submitButtonClicked());
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // Handle file upload
        // Capable of accepting any file type
        MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.addFinishedListener(e -> {
            this.uploadCompleted = true;
            for (String fileName : buffer.getFiles()) {
                inputStreamHashMap.put(fileName, buffer.getInputStream(fileName));
            }
        });

        firstName.isRequired();
        lastName.isRequired();
        email.isRequired();

        layout.add(firstName, lastName, email, upload, submitButton);
        layout.setAlignItems(Alignment.CENTER);

        appLayout.setContent(layout);
        add(appLayout);
    }

    /**
     * Save and submit new JobApplication, then shows a confirm dialog
     */
    public void submitButtonClicked() {

        if (!uploadCompleted) {
            Dialog errorDialog = new Dialog();
            errorDialog.add(new H3("Please upload at least one file."));
            errorDialog.open();
            return;
        }

        JobApplication jobApplication = binder.getBean();
        jobApplication.setFirstName(firstName.getValue());
        jobApplication.setLastName(lastName.getValue());
        jobApplication.setEmail(email.getValue());
        jobApplication.setJobId(targetJobId);
        System.out.println("length of inputs");
        System.out.println(inputStreamHashMap.size());
        jobApplicationService.submit(jobApplication, inputStreamHashMap);

        Dialog confirmDialog = new Dialog();
        Button okayButton = new Button("OK");
        confirmDialog.addDetachListener(event ->
                okayButton.getUI().ifPresent(ui -> ui.navigate(JobListingsView.ROUTE + "/applicant")));
        okayButton.addClickListener(event -> confirmDialog.close());
        confirmDialog.add(new H2("Application submitted!"), new Paragraph(), okayButton);
        confirmDialog.open();
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        this.targetJobId = Long.valueOf(parameter);
    }
}
