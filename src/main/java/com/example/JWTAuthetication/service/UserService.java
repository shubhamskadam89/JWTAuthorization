package com.example.JWTAuthetication.service;

import com.example.JWTAuthetication.model.UserEntity;
import com.example.JWTAuthetication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity getUserByUsername(String username) {
        return userRepository.findById(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }
}
