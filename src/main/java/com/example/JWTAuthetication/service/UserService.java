package com.example.JWTAuthetication.service;

import com.example.JWTAuthetication.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    List<User> userList = new ArrayList<>();

    public UserService() {
        userList.add(new User("Chintu", 101, "chintu@gmail.com"));
        userList.add(new User("Mintu", 102, "mintu@gmail.com"));
        userList.add(new User("Pintu", 103, "pintu@gmail.com"));
        userList.add(new User("Shantu", 104, "shantu@gmail.com"));
    }

    public List<User> getUserList() {
        return userList;
    }

    public User getUserByUserID(int userID) {
        for (User user : userList) {
            if (user.getUserID()==(userID)) {
                return user;
            }
        }
        throw new IllegalArgumentException("User with ID " + userID + " not found.");
    }
}
