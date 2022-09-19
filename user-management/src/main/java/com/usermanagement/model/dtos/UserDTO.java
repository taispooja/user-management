package com.usermanagement.model.dtos;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class UserDTO {

	@NotEmpty(message = "First name is required")
    private String firstName;

	@NotEmpty(message = "Last name is required")
    private String lastName;

    @Email(message = "Email address is not valid")
    @NotEmpty(message = "The email address is required")
    private String emailId;

    @NotEmpty(message = "Password is mandatory")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Password Must be at least 8 characters long and it should contain atleat 1 upperCase , 1 lowerCase, 1 number and 1 special character")
    private String password;
    
    private Set<RoleDTO> roles;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<RoleDTO> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleDTO> roles) {
		this.roles = roles;
	}
	
	public UserDTO(String firstName, String lastName, String emailId, String password, Set<RoleDTO> roles) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.password = password;
		this.roles = roles;
	}

	
	

}
