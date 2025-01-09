package com.example.RegistrationDemo.service;

import java.time.LocalDateTime;
import java.util.List;

import com.example.RegistrationDemo.entity.User;
import com.example.RegistrationDemo.repository.UserRepository;

public interface UserService {
	User findUserById(Long id);
	User getUserById(Long id);
	User findUserByEmail(String email);
	User saveUser(User	user);
	List<User> getAllUsers();
	User updatedUser(Long id, User user);
	boolean deletUserById(Long id);
	boolean validateUser(String email, String password);
	String generateResetToken(String email);
	void sendPasswordResetEmail(String email, String token);
	boolean resetPassword(String token, String newPassword);
	boolean hasExpired(LocalDateTime expiryDateTime);
//	String sendEmail(User user);

	boolean validateUserWithSecurityAnswer(String email, String securityanswer);
	
	
//	String generateResetToken(String email);
	
	
}
