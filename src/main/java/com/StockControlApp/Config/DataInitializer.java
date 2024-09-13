package com.StockControlApp.Config;

import com.StockControlApp.Entity.Admin;
import com.StockControlApp.Repositories.AdminRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class DataInitializer {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        String defaultAdminEmail = "farahelbey1998@gmail.com";
        if (!adminRepository.existsByEmail(defaultAdminEmail)) {
            Admin defaultAdmin = new Admin();
            defaultAdmin.setEmail(defaultAdminEmail);
            defaultAdmin.setPassword(passwordEncoder.encode("defaultPassword"));
            defaultAdmin.setNom("Farah");
            defaultAdmin.setPrenom("ElBey");
            adminRepository.save(defaultAdmin);
        }
    }
}
