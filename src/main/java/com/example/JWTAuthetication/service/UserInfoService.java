package com.example.JWTAuthetication.service;

import com.example.JWTAuthetication.model.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserInfoService {

    private final Map<Integer, UserDetails> userDetailsMap = new HashMap<>();

    public UserInfoService() {
        userDetailsMap.put(101, new UserDetails("Admin", "Pune", 50000));
        userDetailsMap.put(102, new UserDetails("Employee", "Mumbai", 30000));
        userDetailsMap.put(103, new UserDetails("Manager", "Delhi", 70000));
        userDetailsMap.put(104, new UserDetails("Intern", "Nagpur", 15000));
    }

    public UserDetails getUserDetailsByID(int userID) {
        if (!userDetailsMap.containsKey(userID)) {
            throw new IllegalArgumentException("UserDetails for ID " + userID + " not found.");
        }
        return userDetailsMap.get(userID);
    }

    public void addUserDetails(int userID, UserDetails userDetails) {
        userDetailsMap.put(userID, userDetails);
    }
    public UserDetails getDetailsByUserID(int uID){
        return userDetailsMap.get(uID);
    }
    public Map<Integer, UserDetails> getALLUserDetails(){
        return userDetailsMap;
    }
}
