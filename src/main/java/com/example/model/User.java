package com.example.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class User {
    @Id
    @GeneratedValue
    private Integer studentId;

	private String email;
	
    private String firstName;

    private String lastName;
    
    private String openID;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="companyId")
    private CompanySubscription companySubscription;

    public User () {
    }
    
    public User(String email, String firstName, String lastName, String openID, CompanySubscription companySubscription) {
    	this.email = email;
    	this.firstName = firstName;
    	this.lastName = lastName;
    	this.openID = openID;
    	this.companySubscription = companySubscription;
    }
    

    public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getOpenID() {
		return openID;
	}

	public void setOpenID(String openID) {
		this.openID = openID;
	}

	public CompanySubscription getCompanySubscription() {
		return companySubscription;
	}

	public void setCompanySubscription(CompanySubscription companySubscription) {
		this.companySubscription = companySubscription;
		if (companySubscription!=null && !companySubscription.getUsers().contains(this)) {
			companySubscription.addUser(this);
		}
			
	}
    
    
}
