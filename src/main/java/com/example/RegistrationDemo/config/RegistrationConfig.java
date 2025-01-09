package com.example.RegistrationDemo.config;

import java.io.ObjectInputFilter.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration 

public class RegistrationConfig {
	  @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        http
	            .authorizeHttpRequests(auth -> auth
	            	
	                .requestMatchers("/admin/**","/edit/*","/delete/*").hasRole("ADMIN") // Only ADMIN role can access
	                .requestMatchers("/user/**").hasRole("USER")  // Only USER role can access
	                .requestMatchers("/").permitAll()   // PUBLIC resources accessible to everyone
	                .requestMatchers("/home").hasAnyRole("USER","ADMIN")
	                .anyRequest().authenticated()           // All other requests require authentication
	            )
//	            .exceptionHandling(exceptionHandling -> exceptionHandling
//	                    .accessDeniedHandler(new CustomAccessDeniedHandler())
//	                )
	           .exceptionHandling(exceptionHandling -> exceptionHandling
	            	    .accessDeniedPage("/error-403")
	            	)
	            .formLogin() // Enable form-based login
	            .and()
	            .logout();   // Enable logout functionality
	        
	        return http.build();
	    }

	    @Bean
	    public UserDetailsService userDetailsService() {
	        // Define an admin user
//	        UserBuilder users = User.withDefaultPasswordEncoder();
	        UserDetails admin=User
	        	.withUsername("admin")
	        	.password("{bcrypt}$2a$10$P0djUjFeC1FsXrvggF4gu.Bfsfir.EJ0AT3phC.pXV0jXhgGVfcv.")
	            .roles("ADMIN") // Assign the ADMIN role
	            .build();
	        System.out.println(admin.getPassword());

	        // Define a standard user
	        UserDetails user = User
	            .withUsername("user")
	            .password("{bcrypt}$2a$10$ws8yEWqcQPaxZfebI8PJuOqhdTZV0VLx6/CGRxpW9Ca06.KkeUNRe")
//	            .password("user123")
	            .roles("USER") // Assign the USER role
	            .build();
	        System.out.println(user.getPassword());
	        // Define a public user
	       
	        return new InMemoryUserDetailsManager(admin, user);
	    }

	}