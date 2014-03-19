package com.example.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.example.model.CompanySubscription;
import com.example.model.Person;
import com.example.model.User;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

@Service
public class EventServiceImpl implements EventService {
    @PersistenceContext
    EntityManager em;
	
	private String ErrorTemplate = "<result>" +
									"<success>false</success>"+
									"<errorCode>%s</errorCode>"+
									"<message>%s</message>"+
									"</result>";
	
	
	public String FetchEvent(String token) throws Exception {
		OAuthConsumer consumer = new DefaultOAuthConsumer("test-7940", "UlhALaaoJ20e6caa");
		//URL url = new URL("https://www.appdirect.com/AppDirect/rest/api/events/"+token);
		URL url = new URL("https://www.appdirect.com/rest/api/events/dummyOrder");
		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		consumer.sign(request);
		request.connect();
		int ResponseCode = request.getResponseCode();
		//String ResponseMessage = request.getResponseMessage();
		if (ResponseCode == 200) {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(request.getInputStream());
			//return doc.getElementsByTagName("type").item(0).getTextContent();
			return HandleEvent(doc);
		}
		else {
			String message = "HTTP response "+ResponseCode;
			String result = String.format(ErrorTemplate, "UNKNOWN ERROR", message);
			return result;
		}
	}
	
	public String HandleEvent(Document doc) {
		String EventType = doc.getElementsByTagName("type").item(0).getTextContent();
		if (EventType.equals("SUBSCRIPTION_ORDER")) {
			return CreateOrder(doc);
		}
		else{
			String message = "Event type "+EventType+" is not configured";
			String result = String.format(ErrorTemplate, "CONFIGURATION_ERROR", message);
			return result;
		}
		
	}
	
	public String CreateOrder(Document doc) {
		/* get creator information */
		CompanySubscription companySubscription =  CreateCompanySubscription(doc);
		User user = CreateUser(doc, companySubscription);
		String accountId = addCompanySubscription(companySubscription);
		addUser(user);
		String result = String.format("<result><success>true</success><accountIdentifier>%s</accountIdentifier></result>", accountId);
		return result;
	}
	
	/* create company subscription from xml */
	public CompanySubscription CreateCompanySubscription(Document doc){
		Element ce = (Element) doc.getElementsByTagName("company").item(0);
		String name = ce.getElementsByTagName("name").item(0).getTextContent();
		String website = ce.getElementsByTagName("website").item(0).getTextContent();
		Element oe = (Element) doc.getElementsByTagName("order").item(0);
		String edition = oe.getElementsByTagName("editionCode").item(0).getTextContent();
		CompanySubscription companySubscription = new CompanySubscription (edition, name, website);
		return companySubscription;
	}
	
	/* create user from xml */
	public User CreateUser(Document doc, CompanySubscription companySubscription) {
		Element e = (Element) doc.getElementsByTagName("creator").item(0);
		String email = e.getElementsByTagName("email").item(0).getTextContent();
		String firstName = e.getElementsByTagName("firstName").item(0).getTextContent();
		String lastName = e.getElementsByTagName("lastName").item(0).getTextContent();
		String openID = e.getElementsByTagName("openId").item(0).getTextContent();
		User user = new User(email, firstName, lastName, openID, companySubscription);
		return user;
	}
	
	 @Transactional
	 public void addUser(User user) {
		 em.persist(user);
		 //em.flush();
	 }
	 
	 @Transactional
	 public String addCompanySubscription(CompanySubscription companySubscription) {
		 em.persist(companySubscription);
		 //em.flush();
		 return companySubscription.getCompanyId().toString();
	 }
}
