package com.example.service;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

import com.example.model.CompanySubscription;
import com.example.model.User;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

public interface EventService {
	public String FetchEvent(String token) throws Exception;
	public String HandleEvent(Document doc);
	public String CreateOrder(Document doc);
	public CompanySubscription CreateCompanySubscription(Document doc);
	public User CreateUser(Document doc, CompanySubscription companySubscription);
	public void persistCompanySubscription(CompanySubscription companySubscription);
	public void persistUser(User user);
}
