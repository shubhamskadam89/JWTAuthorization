package com.example.JWTAuthetication.controller;

import com.example.JWTAuthetication.model.UserEntity;
import com.example.JWTAuthetication.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserRestController {

    @Autowired
    private UserService userService;

    // 🔐 ADMIN only: get all users
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }

    // 🔐 USER/ADMIN: get user by username
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/user/{username}")
    public UserEntity getUser(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }
}
