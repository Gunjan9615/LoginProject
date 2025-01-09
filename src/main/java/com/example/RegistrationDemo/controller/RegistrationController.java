package com.example.RegistrationDemo.controller;


import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.RegistrationDemo.entity.PasswordResetToken;
import com.example.RegistrationDemo.entity.User;
import com.example.RegistrationDemo.repository.TokenRepository;
import com.example.RegistrationDemo.repository.UserRepository;
import com.example.RegistrationDemo.service.UserService;



@Controller
@RequestMapping("/")
public class RegistrationController {

		@Autowired
		private UserService userService;
		
		@Autowired
		private TokenRepository tokenRepository;
		
//		@Autowired
//		private UserRepository userRepo;
		
		@GetMapping("/home")
		public String showHomeForm() {
			System.out.println("I am from home");
			return "home";
			
		}
	
		
		@GetMapping("/admin")
		 public String getAllUser(Model model) {
			System.out.println("I am from get admin");
			 List<User> users = userService.getAllUsers();
		    model.addAttribute("users", users);
	        return "/AdminPage";
	    }
	

	@GetMapping("/user/login")
		public String showLoginForm() {
			System.out.println("I am from getlogin");
			return "login";
			
		}

		
		@PostMapping("/user/login")
			public String handleLogin(@RequestParam String email, @RequestParam String password, Model model) {
			if(userService.validateUser(email, password)) {
				return "welcome";
			}
			else {
				model.addAttribute("error", "Invalid email ID or password.");
				System.out.println("Invalid email ID or password");
				return "login";
			}
		}
		
		//Show the registration form
		@GetMapping("/user/register")
		public String showRegistrationForm(Model model) {
			model.addAttribute("user", new User());
			return "register";
		}

		@PostMapping("/user/save")
		public String saveUser(@Validated @ModelAttribute User user,  BindingResult result, Model model) {
			if(result.hasErrors()) {
				return "register";
			}
				try {
		        userService.saveUser(user); // Pass the User object to the service
		        System.out.println("Save data");
		        model.addAttribute("success_msg", "User saved successfully");
		        return "redirect:/user/login";
		    } catch (Exception e) {
		        model.addAttribute("errorMessage", "Failed to save User: " + e.getMessage());
		        return "register";
		    }
		}
		
		@GetMapping("/user/forgot")
		public String openEmailForm(){
			return "forget_email";
		}
		
		@PostMapping("/user/forgot")
		public String forgotPasswordProcess(@RequestParam String email, @RequestParam String securityanswer,Model model) {
			if(userService.validateUserWithSecurityAnswer( email, securityanswer))
			{
			try {
		            String token = userService.generateResetToken(email);
		            userService.sendPasswordResetEmail(email, token);
		            model.addAttribute("successMessage", "Password reset link sent to your email.");
		            return "redirect:/user/forgot";
		        } catch (Exception e) {
		            model.addAttribute("errorMessage", e.getMessage());
		        }
			}
			return "redirect:/user/forgot";
		}
		
		
		@GetMapping("resetPassword/{token}")
		public String resetPasswordFrom(@PathVariable String token, Model model) {
			PasswordResetToken reset=tokenRepository.findByToken(token);
			if(reset!=null && userService.hasExpired(reset.getExpiryDateTime())) {
				model.addAttribute("email",reset.getUser().getEmail());
				return "resetPassword";
			}
			return "redirect:/forgot_password";
		}
		
		@PostMapping("resetPassword/{token}")
				public String passwordResetProcess(@ModelAttribute User user) {
				User user1=userService.findUserByEmail(user.getEmail());
				if(user1!=null) {
					user.setPassword(user.getPassword());
					userService.saveUser(user1);
				}
				return "return:redirect:/login";
			}

		
		@GetMapping("/error-403")
	    public String accessDenied() {
	        return "error-403";
	    }
		
		//PUT mapping Edit designation
		
		@GetMapping("/edit/{id}")
		public String showUpdatePage(@PathVariable Long id, Model model) {
		    User user = userService.findUserById(id);
		    if (user == null) {
		        throw new IllegalArgumentException("User not found with ID: " + id);
		    }
		    model.addAttribute("user", user);
		    return "updatePage";
		}

		@PostMapping("/edit/{id}")
		public String updateUser(@PathVariable Long id, @ModelAttribute("user") User user, Model model) {
		    try {
		        User updatedUser = userService.updatedUser(id, user);
		        model.addAttribute("message", "User details updated successfully!");
		        model.addAttribute("user", updatedUser);
		        return "redirect:/admin";
		    } catch (Exception e) {
		        model.addAttribute("message", "Error updating user: " + e.getMessage());
		        return "updatePage";
		    }
		}
	    
		 //Delete Mapping 
	    @GetMapping("/delete/{id}")
	    public String deleteUser(@PathVariable Long id, Model model) {
	    	System.out.println("I am from delete mapping ");
	    	try {
	            userService.deletUserById(id);
	            model.addAttribute("successMessage", "User deleted successfully.");
	        } catch (Exception e) {
	            model.addAttribute("errorMessage", "Error deleting user: " + e.getMessage());
	        }
	        // Redirect to the admin page after deletion
	        return "redirect:/admin";
	    }
	   

}
