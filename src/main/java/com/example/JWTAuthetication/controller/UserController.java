package com.example.JWTAuthetication.controller;

import com.example.JWTAuthetication.JWT.AuthTokenFilter;
import com.example.JWTAuthetication.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import javax.sql.DataSource;

@RestController
@RequestMapping("/new")
@Configuration
public class UserController {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    DataSource dataSource;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/user")
    public String createUser(@RequestParam String username,
                             @RequestParam String password,
                             @RequestParam String role){
        JdbcUserDetailsManager userDetailsManager =
                new JdbcUserDetailsManager(dataSource);
        if(userDetailsManager.userExists(username)){
            logger.warn("User {} already exist",username);
            return "User already exist";
        }
        UserDetails user = User.withUsername(username)
                .password(passwordEncoder.encode(password)).roles(role).build();
        userDetailsManager.createUser(user);

        return "User created Successfully";
    }



}

