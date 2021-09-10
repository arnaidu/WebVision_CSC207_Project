package com.csc.spring.service.implementations;

import com.csc.spring.db.JobPostingRepo;
import com.csc.spring.models.JobPosting;
import com.csc.spring.service.JobPostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JobPostingServiceImpl implements JobPostingService {

    // Inject all necessary repositories
    @Autowired
    private JobPostingRepo jobPostingRepo;

    @Override
    public void save(JobPosting jobPosting) {
        jobPostingRepo.save(jobPosting);
    }

    @Override
    public void delete(JobPosting jobPosting) {
        jobPostingRepo.delete(jobPosting);
    }

    @Override
    public void delete(String name) {
        jobPostingRepo.delete(jobPostingRepo.findByName(name));
    }

    @Override
    public void delete(Long id) {
        jobPostingRepo.delete(Objects.requireNonNull(jobPostingRepo.findById(id).orElse(null)));
    }

    /**
     *
     * @param name
     * @return Job posting with given name
     */
    @Override
    public JobPosting findByName(String name) {
        return jobPostingRepo.findByName(name);
    }

    /**
     *
     * @param id
     * @return Job posting given id
     */
    @Override
    public JobPosting findById(Long id) {
        return jobPostingRepo.findById(id).orElse(null);
    }

    /**
     *
     * @return all job postings in repo
     */
    @Override
    public Collection<JobPosting> getAll() {
        return jobPostingRepo.findAll();
    }

    /**
     * Test data
     */
    @Override
    public void generateTestData() {
        String[] titles = new String[]{"Software Engineer", "Machine Learning Intern", "Product Manager",
                "Database Developer", "Data Security Analyst", "UX Designer"};
        String[] descriptions = new String[]{"Build web services in Clojure or Go that help enable mobile commerce for retailers and restaurants",
                "Take charge and manage important state-of-the-art machine learning and deep learning projects with billions of data points from day one",
                "A comprehensive product roadmap capturing the goals of your product's strategy and vision",
                "Create scalable, reliable, and creative solutions that build on industry-leading technology.",
                "Conduct and manage vulnerability scans using external and internal tools.",
                "You will own research, interaction design and IA in a cross-functional product team"};
        String[] requirements = new String[]{"Resume", "Cover Letter"};
        String[] companies = new String[]{"Amazon", "Dessa", "Amazon", "REDSpace", "Amazon", "Vision Critical"};
        for (int i = 0; i < 6; i++) {
            jobPostingRepo.save(new JobPosting(titles[i], descriptions[i], requirements, companies[i],
                    3, "Java,C++,Python", Calendar.getInstance()));
        }
    }

    /**
     *
     * @param name
     * @return filter and return in collection all postings by company
     */
    @Override
    public Collection<JobPosting> findByCompany(String name) {
        return jobPostingRepo.findAllByCompany(name);
    }
}
