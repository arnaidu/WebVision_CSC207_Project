package com.csc.spring.views;

import com.csc.spring.models.Document;
import com.csc.spring.models.JobApplication;
import com.csc.spring.service.ApplicantService;
import com.csc.spring.service.JobApplicationService;
import com.csc.spring.util.CurrentUser;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.olli.FileDownloadWrapper;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

@Route(ApplicationHistoryView.ROUTE)
public class ApplicationHistoryView extends CommonView {

    public static final String ROUTE = "applications";

    private final JobApplicationService jobApplicationService;
    private final ApplicantService applicantService;

    private Grid<JobApplication> grid = new Grid<>(JobApplication.class);
    private Grid<Document> documentGrid = new Grid<>(Document.class);
    private Button deleteBtn = new Button("Delete");
    private VerticalLayout layout = new VerticalLayout();
    private Button downloadFiles = new Button("Download Files");

    private JobApplication selectedApp;

    /**
     * Create the application history to be seen by applicants
     * @param jobApplicationService
     * @param applicantService
     */
    @Autowired
    public ApplicationHistoryView(JobApplicationService jobApplicationService,
                                  ApplicantService applicantService) {
        this.jobApplicationService = jobApplicationService;
        this.applicantService = applicantService;

        downloadFiles.setEnabled(false);

        grid.setColumns("dateCreated", "jobName", "applicationStatus");

        deleteBtn.addClickListener(event -> deleteApplication());
        layout.add(grid, deleteBtn);
        appLayout.setContent(layout);
        add(appLayout);

        grid.asSingleSelect().addValueChangeListener(event -> {
            selectedApp = grid.asSingleSelect().getValue();
            downloadFiles.setEnabled(true);
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
            layout.add(documentGrid, downloadButtons);
        });

        updateGrid();
    }

    /**
     * updates the grid whenever the view is gone to
     */
    public void updateGrid() {
        if (jobApplicationService != null) {
            grid.setItems(applicantService.getAllApplications(CurrentUser.currentApplicant));
        }

    }

    /**
     * Allows user to delete an application
     */

    public void deleteApplication() {
        jobApplicationService.withdraw(selectedApp);
        updateGrid();
    }

}
