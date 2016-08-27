package com.jlearning.shopping.model;

import java.util.UUID;


public class CustomerByUsername {
	
	private String username;
	
	private String email;
	
	private UUID customerId;
	
	private String password;
	
	private String firstname;
	
	private String lastname;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UUID getCustomerId() {
		return customerId;
	}

	public void setCustomerId(UUID customerId) {
		this.customerId = customerId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public void mapCustomerInfoToCustomerObject(CustomerByUsername customerInfo){
		this.email = customerInfo.getEmail();
		this.firstname = customerInfo.getFirstname();
		this.lastname = customerInfo.getLastname();
		this.customerId = customerInfo.getCustomerId();
	}
}
