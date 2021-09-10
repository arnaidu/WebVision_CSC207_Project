package com.csc.spring.views;

import com.csc.spring.models.JobPosting;
import com.csc.spring.util.CurrentUser;
import com.github.appreciated.card.ClickableCard;
import com.github.appreciated.card.content.IconItem;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class JobCard extends VerticalLayout {
    private UI ui;
    private ClickableCard card;
    private Image img;
    private JobPosting jobPosting;
    private boolean coordinatorCard = false;

    /**
     * Creates a job posting and puts it into a clickable card to see description/information
     * @param imagePath
     * @param jobPosting
     * @param urlParameter
     */
    public JobCard(String imagePath, JobPosting jobPosting, String urlParameter) {
        super();

        System.out.println("Job Card role");
        System.out.println(CurrentUser.currentUser.getRole());
        if (!urlParameter.equals("applicant")) {
            coordinatorCard = true;
        }

        img = new Image(imagePath, "image");
        img.setWidth("50px");
        img.setHeight("50px");
        this.jobPosting = jobPosting;

        card = new ClickableCard(
                event -> showJobDetails(jobPosting), new IconItem(img, jobPosting.getName(), jobPosting.getDescription())
        );
        card.setWidth("80%");
        add(card);
    }

    /**
     * This formats the details shown to the user
     * @param jobPosting
     */
    public void showJobDetails(JobPosting jobPosting) {
        Dialog dialog = new Dialog();
        HorizontalLayout userControls = new HorizontalLayout();
        Button applyBtn = new Button("Apply");
        applyBtn.addClickListener(event -> {
            dialog.close();
            applyBtn.getUI().ifPresent(ui -> ui.navigate(JobApplyView.ROUTE + "/" + jobPosting.getId()));
        });
        Button modifyBtn = new Button("Modify");
        modifyBtn.addClickListener(event -> {
            dialog.close();
            modifyBtn.getUI().ifPresent(ui -> ui.navigate(JobPostingEditor.ROUTE + "/" + jobPosting.getId()));
        });
        Button seeApps = new Button("View Applications");
        seeApps.addClickListener(event -> {
            dialog.close();
            seeApps.getUI().ifPresent(ui -> ui.navigate(ApplicationsView.ROUTE + "/" + jobPosting.getId()));
        });

        if (coordinatorCard) {
            userControls.add(modifyBtn, seeApps);
        } else {
            userControls.add(applyBtn);
        }

        UnorderedList reqList = new UnorderedList();
        for (String req : jobPosting.getSkills().split(",")) {
            reqList.add(new ListItem(req));
        }

        dialog.add(new H2(jobPosting.getName() + " @ " + jobPosting.getCompany()),
                new Paragraph("Description\n" + jobPosting.getDescription()),
                new Paragraph("Required Skills: "),
                reqList,
                new Paragraph("Positions remaining: " + jobPosting.getNumAvailable()),
                new Paragraph("Application deadline: " + jobPosting.getDeadline()),
                userControls
        );
        dialog.open();
    }

}