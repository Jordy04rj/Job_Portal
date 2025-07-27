package com.jsp.jobportal.Job_Portal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/applicant")
public class ApplicantController {
    @Autowired private JobRepository jobRepo;
    @Autowired
    private ApplicationRepository appRepo;
    @Autowired private UserRepository userRepo;

    @PostMapping("/apply/{jobId}")
    public String apply(@PathVariable Long jobId, Principal principal) {
        User applicant = userRepo.findByUsername(principal.getName());
        Job job = jobRepo.findById(jobId).orElseThrow();
        Application app = new Application();
        app.setApplicant(applicant);
        app.setJob(job);
        appRepo.save(app);
        return "redirect:/";
    }

    @GetMapping("/applications")
    public String viewApps(Model model, Principal principal) {
        User user = userRepo.findByUsername(principal.getName());
        model.addAttribute("applications", appRepo.findByApplicant(user));
        return "my_applications";
    }
}
