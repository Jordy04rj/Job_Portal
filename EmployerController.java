package com.jsp.jobportal.Job_Portal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/employer")
public class EmployerController {
    @Autowired
    private JobRepository jobRepo;
    @Autowired private UserRepository userRepo;

    @GetMapping("/jobs")
    public String jobs(Model model) {
        model.addAttribute("job", new Job());
        model.addAttribute("jobs", jobRepo.findAll());
        return "employer_jobs";
    }

    @PostMapping("/jobs")
    public String saveJob(@ModelAttribute Job job, Principal principal) {
        User employer = userRepo.findByUsername(principal.getName());
        job.setEmployer(employer);
        jobRepo.save(job);
        return "redirect:/employer/jobs";
    }
}
