package com.example.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;

@Entity
public class CompanySubscription {
	@Id
    @GeneratedValue
    @Column(name="companyId")
    private Integer companyId;
    
    private String edition;

    private String name;

    private String website;
    
    @OneToMany(mappedBy="companySubscription",cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<AppUser> appUsers = new HashSet<AppUser>(); 
    
	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}
	
	@Override
	public String toString() {
		return this.companyId+this.edition+this.name+this.website;
	}
	
	public void addAppUser(AppUser appUser) {
		this.appUsers.add(appUser);
		if (appUser.getCompanySubscription()!=this) {
			appUser.setCompanySubscription(this);
		}
	}
	
	public Set<AppUser> getUsers() {
		return this.appUsers;
	}


}
