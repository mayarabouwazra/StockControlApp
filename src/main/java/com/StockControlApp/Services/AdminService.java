package com.StockControlApp.Services;



import com.StockControlApp.Entity.*;
import com.StockControlApp.Repositories.*;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    private StockAdminRepository stockAdminRepository;

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private EngineerRepository engineerRepository;

    @Autowired
    private EmailService emailService;

    public Project addProject(Project project) {
        return projectRepository.save(project);
    }

    public String assignEngineerToProject(String email, Long projectId) {
        Optional<Engineer> engineerOpt = engineerRepository.findById(email);
        Optional<Project> projectOpt = projectRepository.findById(projectId);

        if (engineerOpt.isPresent() && projectOpt.isPresent()) {
            Engineer engineer = engineerOpt.get();
            Project project = projectOpt.get();
            engineer.getProjects().add(project);
            project.getEngineers().add(engineer);
            engineerRepository.save(engineer);
            projectRepository.save(project);
            return "Engineer assigned to project successfully.";
        } else {
            return "Engineer or Project not found.";
        }
    }
    public Engineer createEngineerProfile(String nom, String prenom, String email, String senderEmail, String senderPassword) throws MessagingException {
        String password = generateRandomPassword();
        Engineer engineer = new Engineer();
        engineer.setNom(nom);
        engineer.setPrenom(prenom);
        engineer.setEmail(email);
        engineer.setPassword(bCryptPasswordEncoder.encode(password));
        Engineer savedEngineer = engineerRepository.save(engineer);

        emailService.sendEmail(
                email,
                "Your Engineer Profile",
                "Your password is: " + password,
                senderEmail,
                senderPassword
        );

        return savedEngineer;
    }

    public StockAdmin createStockAdminProfile(String nom, String prenom, String email, String senderEmail, String senderPassword) throws MessagingException {
        String password = generateRandomPassword();
        StockAdmin stockAdmin = new StockAdmin();
        stockAdmin.setNom(nom);
        stockAdmin.setPrenom(prenom);
        stockAdmin.setEmail(email);
        stockAdmin.setPassword(bCryptPasswordEncoder.encode(password));

        StockAdmin savedStockAdmin = stockAdminRepository.save(stockAdmin);

        emailService.sendEmail(
                stockAdmin.getEmail(),
                "Your Stock Admin Profile",
                "Your password is: " + password,
                senderEmail,
                senderPassword
        );
        return savedStockAdmin;
    }

    public void notifyItemState(Long projectId, boolean isComplete) {
        Project project = projectRepository.findById(projectId).orElse(null);
        if (project != null) {
            String status = isComplete ? "complete" : "not complete";
            System.out.println("Notification to admin: The stock for project " + project.getProjectName() + " is " + status + ".");
        }
    }
    private String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        int length = 10;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            password.append(characters.charAt(random.nextInt(characters.length())));
        }

        return password.toString();
    }
}
