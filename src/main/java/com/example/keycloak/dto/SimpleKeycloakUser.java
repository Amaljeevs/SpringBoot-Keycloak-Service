package com.example.keycloak.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


public class SimpleKeycloakUser {

    private String id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @Email
    private String email;
    
    private String roleName;
    
    public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@NotBlank
    private String username;
    @NotBlank
    private String password;

    // add any other attributes that you want to be separated from keycloak
    private String otherAttribute;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOtherAttribute() {
		return otherAttribute;
	}

	public void setOtherAttribute(String otherAttribute) {
		this.otherAttribute = otherAttribute;
	}
    
    
}

