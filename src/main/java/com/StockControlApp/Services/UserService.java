package com.StockControlApp.Services;

import com.StockControlApp.Entity.Admin;
import com.StockControlApp.Entity.Engineer;
import com.StockControlApp.Entity.StockAdmin;
import com.StockControlApp.Entity.User;
import com.StockControlApp.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    public boolean validateUserCredentials(String email, String rawPassword) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return bCryptPasswordEncoder.matches(rawPassword, user.getPassword());
        }
        return false;
    }

    public boolean userExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    public boolean changePassword(String email, String currentPassword, String newPassword) {
        User user = userRepository.findByEmail(email);
        if (user != null && bCryptPasswordEncoder.matches(currentPassword, user.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(newPassword));
            userRepository.save(user);
            return true;
        }
        return false;
    }
    public boolean isAdmin(String email) {
        User user = userRepository.findByEmail(email);
        return user instanceof Admin;
    }
    public boolean isEngineer(String email) {
        User user = userRepository.findByEmail(email);
        return user instanceof Engineer;
    }


    public boolean isStockAdmin(String email) {
        User user = userRepository.findByEmail(email);
        return user instanceof StockAdmin;
    }

}
