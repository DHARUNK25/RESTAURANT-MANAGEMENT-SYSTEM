package com.PersonDetails;

import java.sql.Date;

public class Employee extends Account {
	private String firstName;
	private String lastName;
	private Date dateOfBirth;
	private String gender;
	private String email;
	private String phoneNumber;
	private double salary;
	private Date hireDate;
	
    public Employee(String firstName, String lastName,String userName,String password, Date dateOfBirth, String gender, String phoneNumber, String email, double salary, Date hireDate,String role) {
        super(userName, password, role);
    	this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.salary = salary;
        this.hireDate = hireDate;
    
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
	public double getSalary() {
		return salary;
	}		
	public void setSalary(double salary) {
		this.salary = salary;
	}
	public Date getHireDate() {
		return hireDate;
	}		
	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}
   

}
