package com.example.RegistrationDemo.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.*;

@Entity
@Table(name="personaldetails")
public class User {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//Ensure Auto increment 
    private Long id; // Primary key										

   
    @Column(name = "fullname", nullable = false)
    private String fullname;
	
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Column(name = "email", nullable = false)
	private String email;
	
    @Column(name = "phone", nullable = false)
	private String phone;
	
    @Column(name = "dob", nullable = false)
	private String dob;
	
    @Column(name = "gender", nullable = false)
	private String gender;
	
    @Column(name = "address1", nullable = false)
//    @JsonProperty("address1")
//    @NotNull(message="address1 can not be null")
	private String address1;
	
    @Column(name = "address2", nullable = false)
     private String address2;
	
    @Column(name = "postalcode", nullable = false)
	private String postalcode;
	
    @Column(name = "state", nullable = false)
	private String state;
	
    @Column(name = "country", nullable = false)
	private String country;
	
//    @Column(name = "profilepic")
//	private String profilepic;
    
    @OneToOne(mappedBy="user", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    private PasswordResetToken passwordResetToken;

    
    @NotBlank(message = "Password is mandatory")
    @Column(name = "password", nullable = false)
	private String password;
	
    @Transient
	private String confirmpassword;
	
    @Column(name = "securityquestion", nullable = false)
	private String securityquestion;
	
    @Column(name = "securityanswer", nullable = false)
	private String securityanswer;
	
    @Column(name = "terms", nullable = false)
	private String terms;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(Long id, String fullname,
			@NotBlank(message = "Email is mandatory") @Email(message = "Email should be valid") String email,
			String phone, String dob, String gender, String address1, String address2, String postalcode, String state,
			String country, PasswordResetToken passwordResetToken,
			@NotBlank(message = "Password is mandatory") String password, String confirmpassword,
			String securityquestion, String securityanswer, String terms) {
		super();
		this.id = id;
		this.fullname = fullname;
		this.email = email;
		this.phone = phone;
		this.dob = dob;
		this.gender = gender;
		this.address1 = address1;
		this.address2 = address2;
		this.postalcode = postalcode;
		this.state = state;
		this.country = country;
		this.passwordResetToken = passwordResetToken;
		this.password = password;
		this.confirmpassword = confirmpassword;
		this.securityquestion = securityquestion;
		this.securityanswer = securityanswer;
		this.terms = terms;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public PasswordResetToken getPasswordResetToken() {
		return passwordResetToken;
	}

	public void setPasswordResetToken(PasswordResetToken passwordResetToken) {
		this.passwordResetToken = passwordResetToken;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmpassword() {
		return confirmpassword;
	}

	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}

	public String getSecurityquestion() {
		return securityquestion;
	}

	public void setSecurityquestion(String securityquestion) {
		this.securityquestion = securityquestion;
	}

	public String getSecurityanswer() {
		return securityanswer;
	}

	public void setSecurityanswer(String securityanswer) {
		this.securityanswer = securityanswer;
	}

	public String getTerms() {
		return terms;
	}

	public void setTerms(String terms) {
		this.terms = terms;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", fullname=" + fullname + ", email=" + email + ", phone=" + phone + ", dob=" + dob
				+ ", gender=" + gender + ", address1=" + address1 + ", address2=" + address2 + ", postalcode="
				+ postalcode + ", state=" + state + ", country=" + country + ", passwordResetToken="
				+ passwordResetToken + ", password=" + password + ", confirmpassword=" + confirmpassword
				+ ", securityquestion=" + securityquestion + ", securityanswer=" + securityanswer + ", terms=" + terms
				+ "]";
	}

}