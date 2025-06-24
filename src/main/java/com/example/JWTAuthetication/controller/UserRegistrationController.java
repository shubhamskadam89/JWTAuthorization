package com.example.JWTAuthetication.controller;

import com.example.JWTAuthetication.model.CreateUserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;

@RestController
@RequestMapping("/new")
@Configuration
public class UserRegistrationController {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    DataSource dataSource;
    private static final Logger logger = LoggerFactory.getLogger(UserRegistrationController.class);

    @PostMapping("/user")
    public String createUser(@RequestBody CreateUserRequest request) {
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);

        if (userDetailsManager.userExists(request.getUsername())) {
            logger.warn("User {} already exists", request.getUsername());
            return "User already exists";
        }

        UserDetails user = User.withUsername(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(request.getRole()) // just "USER" or "ADMIN"
                .build();

        userDetailsManager.createUser(user);
        return "User created Successfully";
    }





}

