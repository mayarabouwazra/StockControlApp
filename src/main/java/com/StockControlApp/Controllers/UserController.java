package com.StockControlApp.Controllers;

import com.StockControlApp.Entity.User;
import com.StockControlApp.Security.CustomUserDetails;
import com.StockControlApp.Security.JWTUtil;
import com.StockControlApp.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/User")
public class UserController {

    @Autowired

    private UserService userService;


    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @CrossOrigin(origins = {"http://localhost:59827", "http://172.16.20.104"}, allowCredentials = "true")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User request) {
        boolean isValid = userService.validateUserCredentials(request.getEmail(), request.getPassword());

        if (isValid) {
            String token = jwtUtil.generateToken(request.getEmail());

            System.out.println("Generated Token: " + token);

            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "email", request.getEmail()
            ));
        } else {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }





    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String currentPassword = request.get("currentPassword");
        String newPassword = request.get("newPassword");

        boolean isChanged = userService.changePassword(email, currentPassword, newPassword);

        if (isChanged) {
            return ResponseEntity.ok("Password changed successfully");
        } else {
            return ResponseEntity.status(400).body("Invalid current password or user not found");
        }
    }

}
