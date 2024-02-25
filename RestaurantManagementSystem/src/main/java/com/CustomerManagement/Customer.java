package com.CustomerManagement;

import java.sql.Date;

import com.PersonDetails.Account;

public class Customer extends Account {
	private String firstName;
	private String lastName;
	private Date dateOfBirth;
	private String gender;
	private String phoneNumber;
	private String email;
	private String city;
	
	
    public Customer(String firstName, String lastName,String userName,String password, Date dateOfBirth, String gender, String phoneNumber, String email, String city, String role) {
        super(userName, password, role);
    	this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.city = city;
    
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
	public Date getDateOfBirth() {
		return dateOfBirth;
	}	
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}	
	public String getGender() {
		return gender;
	}		
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}	
	public String getEmail() {
		return email;
	}		
	public void setEmail(String email) {
		this.email = email;
	}	
	public String getCity() {
		return city;
	}		
	public void setCity(String city) {
		this.city = city;
	}	
   
}
