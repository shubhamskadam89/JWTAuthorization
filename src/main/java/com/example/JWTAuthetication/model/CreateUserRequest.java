// CreateUserRequest.java
package com.example.JWTAuthetication.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateUserRequest {
    // Getters & setters
    private String username;
    private String password;
    private String role;

}
