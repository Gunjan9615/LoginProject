package com.example.RegistrationDemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.RegistrationDemo.entity.PasswordResetToken;
import com.example.RegistrationDemo.entity.User;

public interface TokenRepository extends JpaRepository<PasswordResetToken, Integer> {

	PasswordResetToken findByToken(String token);

}
