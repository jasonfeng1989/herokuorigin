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
    //@GeneratedValue
    private Integer userId;

	private String email;
	
    private String firstName;

    private String lastName;
    
    private String openID;
    /*
    @ManyToOne
    @JoinColumn(name="companyId")
    private CompanySubscription companySubscription;
*/

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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
	
	@Override
	public String toString() {
		return this.userId+this.email+this.firstName+this.lastName+this.openID;
	}
/*
	public CompanySubscription getCompanySubscription() {
		return companySubscription;
	}

	public void setCompanySubscription(CompanySubscription companySubscription) {
		this.companySubscription = companySubscription;
		if (companySubscription!=null && !companySubscription.getUsers().contains(this)) {
			companySubscription.addUser(this);
		}
			
	}
*/

    
    
}
