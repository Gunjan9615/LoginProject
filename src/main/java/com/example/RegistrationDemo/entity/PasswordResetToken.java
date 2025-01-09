package com.example.RegistrationDemo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="passwordResetToken")
public class PasswordResetToken {

	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String token;
	private LocalDateTime expiryDateTime;
	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="user_id", referencedColumnName="id", nullable=false)
	
	private User user;
	public PasswordResetToken() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PasswordResetToken(int id, String token, LocalDateTime expiryDateTime, User user) {
		super();
		this.id = id;
		this.token = token;
		this.expiryDateTime = expiryDateTime;
		this.user = user;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public LocalDateTime getExpiryDateTime() {
		return expiryDateTime;
	}
	public void setExpiryDateTime(LocalDateTime expiryDateTime) {
		this.expiryDateTime = expiryDateTime;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public String toString() {
		return "PasswordResetToken [id=" + id + ", token=" + token + ", expiryDateTime=" + expiryDateTime + ", user="
				+ user + "]";
	}
	
}
