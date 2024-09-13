package com.StockControlApp.Config;

import com.StockControlApp.Security.JWTRequestFilter;
import com.StockControlApp.Security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.servlet.Filter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import jakarta.servlet.Filter;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Properties;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("farahelbey1998@gmail.com");
        mailSender.setPassword("your-email-password"); // Replace with actual email password
        mailSender.setJavaMailProperties(getMailProperties());
        return mailSender;
    }

    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.debug", "true");
        return properties;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/User/login").permitAll()
                        .requestMatchers("/admin/**").hasAuthority("Admin")
                        .requestMatchers("/stock/**").hasAuthority("StockAdmin")
                        .requestMatchers("/Engineer/**").hasAuthority("Engineer")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtRequestFilter(jwtUtil, userDetailsService), UsernamePasswordAuthenticationFilter.class); // Add JWT filter here

        return http.build();
    }

    @Bean
    public JWTRequestFilter jwtRequestFilter(JWTUtil jwtUtil, UserDetailsService userDetailsService) {
        return new JWTRequestFilter(jwtUtil, userDetailsService);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
