package com.csc.spring.views;

import com.csc.spring.models.JobPosting;
import com.csc.spring.service.JobPostingService;
import com.csc.spring.util.CurrentUser;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

@Route(JobPostingEditor.ROUTE)
public class JobPostingEditor extends CommonView implements HasUrlParameter<String> {
    public static final String ROUTE = "jobeditor";

    private final JobPostingService jobPostingService;

    private TextField name = new TextField("Name");
    private TextField description = new TextField("Description");
    private Paragraph company = new Paragraph();
    private CheckboxGroup<String> requirementsBoxes = new CheckboxGroup<>();
    private CheckboxGroup<String> skillsBoxes = new CheckboxGroup<>();
    private ComboBox<Integer> maxApplicantsBoxes = new ComboBox<>();
    private int maxApplicants;
    private DatePicker datePicker = new DatePicker();
    private Calendar calendar = Calendar.getInstance();
    private Button saveButton = new Button("Save");

    private JobPosting jobPosting;
    private Binder<JobPosting> binder = new Binder<>(JobPosting.class);

    @Autowired
    public JobPostingEditor(JobPostingService jobPostingService) {
        this.jobPostingService = jobPostingService;
    }

    public void saveJobPosting() {
        JobPosting jobPosting = binder.getBean();
        jobPosting.setCompany(CurrentUser.getCompany());
        jobPosting.setRequirements(getCheckedRequirements());
        jobPosting.setSkills(getSkills());
        jobPosting.setNumAvailable(maxApplicants);
        jobPosting.setDeadline(calendar);
        jobPostingService.save(jobPosting);
        saveButton.getUI().ifPresent(ui -> ui.navigate(JobListingsView.ROUTE + "/" + CurrentUser.getCompany()));
    }

    public String getSkills() {
        StringBuilder skills = new StringBuilder();
        for (String s : skillsBoxes.getSelectedItems()) {
            skills.append(s + ",");
        }
        return skills.toString().substring(0, skills.length() - 1);
    }

    public String[] getCheckedRequirements() {
        Object[] reqs = requirementsBoxes.getSelectedItems().toArray();
        String[] reqsString = new String[reqs.length];
        for (int i = 0; i < reqs.length; i++) {
            reqsString[i] = (String) reqs[i];
        }
        return reqsString;
    }

    public void setUpView() {
        String companyName = CurrentUser.getCompany();
        company.setText("Company: \n" + companyName);
        name.isRequired();
        description.isRequired();
        requirementsBoxes.isRequired();
        requirementsBoxes.setLabel("Required Documents");
        requirementsBoxes.setItems("Resume", "Cover Letter", "References");
        requirementsBoxes.isRequired();
        skillsBoxes.setLabel("Required Skills");
        skillsBoxes.setItems("Java", "C++", "Python", "Swift", "MATLAB", "Go", "SQL");
        maxApplicantsBoxes.isRequired();
        maxApplicantsBoxes.setItems(1, 2, 3, 4, 5);
        maxApplicantsBoxes.addValueChangeListener(event -> maxApplicants = maxApplicantsBoxes.getValue());
        maxApplicantsBoxes.setLabel("Positions Available");
        datePicker.isRequired();
        datePicker.addValueChangeListener(event -> {
            LocalDate selectedDate = datePicker.getValue();

            // getMonthValue returns 1 for January, while Calendar.JANUARY is 0
            calendar.set(selectedDate.getYear(), selectedDate.getMonthValue() - 1, selectedDate.getDayOfMonth());
        });
        datePicker.setLabel("Application Deadline");

        saveButton.addClickListener(event -> saveJobPosting());

        binder.bindInstanceFields(this);

        add(name, description, company, requirementsBoxes, skillsBoxes,
                maxApplicantsBoxes, datePicker, saveButton);
        this.setAlignItems(Alignment.CENTER);
    }

    /**
     * Binder binds the fields of this class to those with the same name in JobPosting, then sets up rest of view
     *
     * @param event     This is executed after navigation to the job editor view
     * @param parameter The string that appears after the "/"
     */
    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        if (parameter.isEmpty() || parameter.equals("new")) {
            jobPosting = new JobPosting();
        } else {
            Long jobId = Long.valueOf(parameter);
            System.out.println(jobId);
            jobPosting = jobPostingService.findById(jobId);
        }
        binder.setBean(jobPosting);
        setUpView();
    }
}
