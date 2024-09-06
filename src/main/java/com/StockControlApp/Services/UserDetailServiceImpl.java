package com.StockControlApp.Services;


import com.StockControlApp.Entity.Engineer;
import com.StockControlApp.Repositories.EngineerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.stereotype.Service;

import static org.springframework.security.core.userdetails.User.withUsername;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private EngineerRepository engineerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Engineer engineer = engineerRepository.findByEmail(email);
        if (engineer == null) {
            throw new UsernameNotFoundException("Engineer not found.");
        }
        UserBuilder builder = withUsername(engineer.getEmail());
        builder.password(engineer.getPassword());
        builder.roles("ENGINEER");
        return builder.build();
    }

}
