package com.StockControlApp.Controllers;

import com.StockControlApp.Entity.Engineer;
import com.StockControlApp.Entity.Project;
import com.StockControlApp.Entity.StockAdmin;
import com.StockControlApp.Services.AdminService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value="/Admin", produces= MediaType.APPLICATION_JSON_VALUE)
public class AdminController {
    @Autowired
    private  final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/project")
    public Project addProject(@RequestBody @Valid Project project) {
        return adminService.addProject(project);
    }

    @PostMapping("/assign-engineer")
    public String assignEngineerToProject(@RequestParam String email, @RequestParam Long projectId) {
        return adminService.assignEngineerToProject(email, projectId);
    }
    @PostMapping("/createEngineer")
    public Engineer createEngineerProfile(@RequestBody Map<String, String> engineerDetails) throws MessagingException {
        String nom = engineerDetails.get("nom");
        String prenom = engineerDetails.get("prenom");
        String email = engineerDetails.get("email");
        String senderEmail = "farahelbey1998@gmail.com"; // Replace with actual sender email
        String senderPassword = "your-sender-password";        // Replace with actual sender password
        return adminService.createEngineerProfile(nom, prenom, email, senderEmail, senderPassword);
    }

    @PostMapping("/createStockAdmin")
    public StockAdmin createStockAdminProfile(@RequestBody Map<String, String> stockAdminDetails) throws MessagingException {
        String nom = stockAdminDetails.get("nom");
        String prenom = stockAdminDetails.get("prenom");
        String email = stockAdminDetails.get("email");
        String senderEmail = "your-sender-email@example.com";
        String senderPassword = "your-sender-password";
        return adminService.createStockAdminProfile(nom, prenom, email, senderEmail, senderPassword);
    }


}
