package com.example.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CompanySubscription {

    private String companyId;
    
    private String edition;

    private String name;

    private String website;
    
    public CompanySubscription() {
    }
    
    public CompanySubscription(String edition, String name, String website) {
    	this.edition = edition;
    	this.name = name;
    	this.website = website;
    }

	@Id
    @GeneratedValue
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
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


}
