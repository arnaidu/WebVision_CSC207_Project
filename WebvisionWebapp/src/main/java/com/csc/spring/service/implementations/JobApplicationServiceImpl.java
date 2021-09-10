package com.csc.spring.service.implementations;

import com.csc.spring.db.ApplicantRepo;
import com.csc.spring.db.DocumentRepo;
import com.csc.spring.db.JobApplicationRepo;
import com.csc.spring.db.JobPostingRepo;
import com.csc.spring.models.*;
import com.csc.spring.service.ApplicantService;
import com.csc.spring.service.JobApplicationService;
import com.csc.spring.util.CurrentUser;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

@Service
public class JobApplicationServiceImpl implements JobApplicationService {

    // Inject all the necessary repositories
    private final JobApplicationRepo jobApplicationRepo;
    private final DocumentRepo documentRepo;
    private final JobPostingRepo jobPostingRepo;
    private final ApplicantRepo applicantRepo;

    /**
     * This does the injection of repos
     * @param jobApplicationRepo
     * @param documentRepo
     * @param jobPostingRepo
     * @param applicantRepo
     */
    @Autowired
    public JobApplicationServiceImpl(JobApplicationRepo jobApplicationRepo,
                                     DocumentRepo documentRepo,
                                     JobPostingRepo jobPostingRepo,
                                     ApplicantRepo applicantRepo) {
        this.jobApplicationRepo = jobApplicationRepo;
        this.documentRepo = documentRepo;
        this.jobPostingRepo = jobPostingRepo;
        this.applicantRepo = applicantRepo;
    }

    /**
     * Allow submitting application
     * @param jobApplication new job application
     * @param files A Map from filename to its input stream object (its data)
     */
    @Override
    public void submit(JobApplication jobApplication, HashMap<String, InputStream> files) {
        ArrayList<Document> uploadedDocuments = new ArrayList<>();

        for (String fileName : files.keySet()) {

            try {
                uploadedDocuments.add(new Document(fileName, files.get(fileName)));

            } catch (IOException e) {
                System.out.println("Application Submit Error");
            }
        }
        // sets remaining fields of the new job posting
        JobPosting targetJobPosting = jobPostingRepo.findById(jobApplication.getJobId()).orElse(null);
        assert targetJobPosting != null;
        jobApplication.setJobName(targetJobPosting.getName());
        jobApplication.setDocuments(uploadedDocuments);

        CurrentUser.currentApplicant.addApplication(jobApplication);
        applicantRepo.save(CurrentUser.currentApplicant);   // Applicant object was modified, needs to be saved
        jobApplicationRepo.save(jobApplication);
        documentRepo.saveAll(uploadedDocuments);
    }

    /**
     * Allow user to withdraw an application
     * @param jobApplication this job application
     */
    @Override
    public void withdraw(JobApplication jobApplication) {
        if (jobApplication != null) {
            CurrentUser.currentApplicant.removeApplication(jobApplication);
            jobApplicationRepo.delete(jobApplication);  //TODO: does tgis delete Document objects too?
        }
    }

    /**
     * Set the status of an application using the enum ApplicationStatus
     * @param jobApplication This job application
     * @param status The state of this job application (enum)
     */
    @Override
    public void setStatus(JobApplication jobApplication, ApplicationStatus status) {
        jobApplication.setApplicationStatus(status);
    }

    /**
     *
     * @return Collection of all JobApplication objects
     */
    @Override
    public Collection<JobApplication> findAll() {
        return jobApplicationRepo.findAll();
    }

    @Override
    public Collection<JobApplication> findByJobApplication(Long jobId) {
        return jobApplicationRepo.findAllByJobId(jobId);
    }

    @Override
    public Collection<JobApplication> findByEmail(String email){
        return jobApplicationRepo.findAllByEmail(email);
    }

    /**
     *
     * @param jobApplication This JobApplication model
     * @return The document objects associated with this job application
     */
    @Override
    public Collection<Document> getDocuments(JobApplication jobApplication) {
        return jobApplication.getDocuments();
    }

    /**
     *
     * @param jobApplication Current application
     * @return file types of all job applications
     */
    @Override
    public ArrayList<String> getFileTypes(JobApplication jobApplication) {
        ArrayList<String> fileTypes = new ArrayList<>();
        for (Document document : jobApplication.getDocuments()) {
            fileTypes.add(document.getFileType());
        }
        return fileTypes;
    }

    /**
     *
     * @param jobApplication A JobApplication model
     * @return The file names of the documents attached to this job application
     */
    @Override
    public ArrayList<String> getFileNames(JobApplication jobApplication) {
        ArrayList<String> fileNames = new ArrayList<>();
        for (Document document : jobApplication.getDocuments()) {
            fileNames.add(document.getName());
        }
        return fileNames;
    }
}
