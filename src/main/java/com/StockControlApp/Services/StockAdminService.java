package com.StockControlApp.Services;

import com.StockControlApp.Entity.Project;
import com.StockControlApp.Repositories.ProjectRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockAdminService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private AdminService adminService;  // Service for admin notifications

    @Autowired
    private EmailService emailService;

    public void updateStockStatus(Long projectId, boolean isComplete, String missingItems) {
        Project project = projectRepository.findById(projectId).orElse(null);
        if (project != null) {
            project.setStockComplete(isComplete);
            projectRepository.save(project);

            // Notify engineer and admin
            String subject = isComplete ? "Stock Status Update: Complete" : "Stock Status Update: Incomplete";
            String body = isComplete
                    ? "The stock status for project " + project.getProjectName() + " is complete."
                    : "The stock status for project " + project.getProjectName() + " is incomplete. Missing items: " + missingItems;

            String senderEmail = System.getenv("SENDER_EMAIL");
            String senderPassword = System.getenv("SENDER_PASSWORD");

            try {
                emailService.sendEmail(
                        project.getEngineerEmail(),
                        subject,
                        body,
                        senderEmail,
                        senderPassword
                );
            } catch (MessagingException e) {
                e.printStackTrace();
            }

            adminService.notifyItemState(projectId, isComplete);
        }
    }

    public void confirmDestination(Long projectId) {
        Project project = projectRepository.findById(projectId).orElse(null);
        if (project != null) {
            project.setDestinationConfirmed(true);
            projectRepository.save(project);

            System.out.println("Notification to Admin: The destination for project " + project.getProjectName() + " has been confirmed.");
        }
    }
}
