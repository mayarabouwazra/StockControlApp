package com.StockControlApp.Controllers;

import com.StockControlApp.Entity.User;
import com.StockControlApp.Security.CustomUserDetails;
import com.StockControlApp.Security.JWTUtil;
import com.StockControlApp.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/User")
public class UserController {

    @Autowired
    private UserService userService;


    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User request) {
        boolean isValid = userService.validateUserCredentials(request.getEmail(), request.getPassword());

        if (isValid) {
            String token = jwtUtil.generateToken(request.getEmail());

            // Get the current authentication and extract the roles
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            // Extract roles/authorities from the authenticated user
            var authorities = userDetails.getAuthorities().stream()
                    .map(grantedAuthority -> grantedAuthority.getAuthority())
                    .collect(Collectors.toList());

            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "email", request.getEmail(),
                    "authorities", authorities
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
