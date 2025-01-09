package com.example.RegistrationDemo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.RegistrationDemo.entity.PasswordResetToken;
import com.example.RegistrationDemo.entity.User;
import com.example.RegistrationDemo.repository.TokenRepository;
import com.example.RegistrationDemo.repository.UserRepository;
@Service

public class UserServiceImpl implements UserService {

	

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TokenRepository tokenRepository;
	
	@Autowired
	JavaMailSender javaMailSender;

    private BCryptPasswordEncoder passwordEncoder= new BCryptPasswordEncoder(9);
    
    //find user by id
    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    //get user by id
    @Override
  	public User getUserById(Long id){
      	Optional<User> user=userRepository.findById(id);
      	return user.orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
      }
    
    //find user by email
    @Override
    public User findUserByEmail(String email) {
//    	User user=userRepository.findByEmail(email);
//    	System.out.println(userRepository.findByEmail(email));
		return userRepository.findByEmail(email);
    	
    }
    //Save User Details
    @Override
   	public User saveUser(User user) {
   		  if (!user.getPassword().equals(user.getConfirmpassword())) {
   		        throw new IllegalArgumentException("Passwords do not match!");
   		    }
   		user.setPassword(passwordEncoder.encode(user.getPassword()));
   	return userRepository.save(user);
   	} 
    //retrive all data from database
    @Override	
    public List<User> getAllUsers() {
    		List<User> user=userRepository.findAll();
////    		for (User users : user) {
//////                String decodedPassword = passwordEncoder(users.getPassword());
//////                users.setPassword(encodedPassword);
    ////
////            }
           return user;
//    		return userRepository.findAll();
    	}
    //update user details
    @Override
    public User updatedUser(Long id, User user) {
        System.out.println("I am from updated user");
       
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setFullname(user.getFullname());
            existingUser.setEmail(user.getEmail());
            existingUser.setPhone(user.getPhone());
            existingUser.setDob(user.getDob());
            existingUser.setAddress1(user.getAddress1());
            existingUser.setAddress2(user.getAddress2());
            existingUser.setState(user.getState());
            existingUser.setCountry(user.getCountry());
            existingUser.setPostalcode(user.getPostalcode());
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(existingUser);

            // Optional: Update password if provided (ensure it’s encoded)
//            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
//                existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
//            }

            // Validate and save the user
      
        }).orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
    }
    @Override
    public boolean deletUserById(Long id) {
    		
    		if(userRepository.existsById(id)) {
    			userRepository.deleteById(id);
    			return true;
    		}else {
    			
//    			throw new IllegalArgumentException("User not Found with ID"+id);
    			return false;
    		}
    	}
    // verify user email ID and password used in login page
    @Override
    public boolean validateUserWithSecurityAnswer(String email, String securityanswer) {
        User user = userRepository.findByEmail(email);
        return user != null && 
               ( securityanswer.equalsIgnoreCase(user.getSecurityanswer()));
    }  
    @Override
    public boolean validateUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        return user != null && passwordEncoder.matches(password, user.getPassword());
    }
    
//    @Override
//    public String sendEmail(User user) {
//    	try {
//    		String resetlink=generateResetToken(user);
//    		SimpleMailMessage msg=new SimpleMailMessage();
//    		msg.setFrom("gunjansherkar@gmail.com");
//    		msg.setTo(user.getEmail());
//    		msg.setSubject("Forgot Password link");
//    		msg.setText("Hello\n\n" + "Please click on the link to reset your password: " + resetlink + "\n\n" + "Regards\n" + "ABC");
//    		javaMailSender.send(msg);
//    		return "success";
//    	}
//    	catch(Exception e) {
//    		e.printStackTrace();
//    		return "error";
//    	}
//    }

//    private String generateResetToken(User user) {
//		UUID uuid=UUID.randomUUID();
//		LocalDateTime currentDateTime=LocalDateTime.now();
//		LocalDateTime expiryDateTime=currentDateTime.plusMinutes(30);
//		PasswordResetToken resetToken=new PasswordResetToken();
//		resetToken.setUser(user);
//		resetToken.setExpiryDateTime(expiryDateTime);
//		resetToken.setUser(user);
//		PasswordResetToken token=tokenRepository.save(resetToken);
//		if(token!=null) {
//			String endpointUrl = "http://localhost:8080/resetPassword";
//			return endpointUrl + "/" + resetToken.getToken();
//		}
//		return "";
//	}
//
    @Override
	public boolean hasExpired(LocalDateTime expiryDateTime) {
		LocalDateTime currentDateTime=LocalDateTime.now();
		return expiryDateTime.isAfter(currentDateTime);
	}
    @Override
    public String generateResetToken(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("No user found with email: " + email);
        }

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(UUID.randomUUID().toString());
        resetToken.setUser(user);
        resetToken.setExpiryDateTime(LocalDateTime.now().plusMinutes(30));
        tokenRepository.save(resetToken);

        return resetToken.getToken();
    }

    @Override
    public void sendPasswordResetEmail(String email, String token) {
        String resetUrl = "http://localhost:8080/resetPassword/" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset Request");
        message.setText("Click the link below to reset your password:\n" + resetUrl);

        javaMailSender.send(message);
    }

    
    @Override
    public boolean resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token);
        if (resetToken == null || resetToken.getExpiryDateTime().isBefore(LocalDateTime.now())) {
            return false;
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        tokenRepository.delete(resetToken);
        return true;
    }
}

