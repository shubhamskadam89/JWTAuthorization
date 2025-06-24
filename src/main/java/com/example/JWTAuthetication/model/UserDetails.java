package com.example.JWTAuthetication.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDetails {
    private String userType;
    private String userAddress;
    private double userSalary;

}
