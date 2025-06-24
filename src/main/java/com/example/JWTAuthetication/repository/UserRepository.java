package com.example.JWTAuthetication.repository;

import com.example.JWTAuthetication.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {
}
