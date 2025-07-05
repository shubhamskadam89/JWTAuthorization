package com.example.JWTAuthetication.controller;

import com.example.JWTAuthetication.JWT.JWTUtils;
import com.example.JWTAuthetication.JWT.LoginRequest;
import com.example.JWTAuthetication.JWT.LoginResponse;
import com.example.JWTAuthetication.model.User;
import com.example.JWTAuthetication.model.UserDetails;
import com.example.JWTAuthetication.service.UserInfoService;
import com.example.JWTAuthetication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class HomeController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserService userService;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;


    //  USER: Get a single user by index
    // URL: https://localhost:8080/user/{index}
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/{uID}")
    public User userEndPoint(@PathVariable int uID) {
        return userService.getUserByUserID(uID); // Implement this in service
    }


    //  ADMIN: Get the whole user list
    // URL: https://localhost:8080/admin
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public List<User> adminEndPoint() {
        return userService.getUserList(); // Already exists
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/details/{uID}")
    public String getUserWithDetails(@PathVariable int uID) {
        User user = userService.getUserByUserID(uID);
        UserDetails details = userInfoService.getDetailsByUserID(uID);
        return "User: " + user.toString() + "\nDetails: " + details.toString();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/allUserDetails")
    public Map<Integer,UserDetails> getAllUserWithDetails() {
        Map<Integer,UserDetails> allUserdetail = userInfoService.getALLUserDetails();
        return allUserdetail;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        } catch (AuthenticationException exception) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "bad credentials");
            map.put("status", false);
            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);

        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        org.springframework.security.core.userdetails.UserDetails userDetails = (org.springframework.security.core.userdetails.UserDetails) authentication.getPrincipal();

        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        LoginResponse response = new LoginResponse(jwtToken,userDetails.getUsername(),roles);

        return ResponseEntity.ok(response);
    }


}
