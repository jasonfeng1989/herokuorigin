package com.example.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.example.model.CompanySubscription;
import com.example.model.Person;
import com.example.model.AppUser;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

@Service
@Transactional
public class EventServiceImpl implements EventService {
	
	
    @PersistenceContext
    EntityManager em;
    
    
	
	//static EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPAService");
	//static EntityManager em = emf.createEntityManager();
	
	private String ErrorTemplate = "<result>" +
									"<success>false</success>"+
									"<errorCode>%s</errorCode>"+
									"<message>%s</message>"+
									"</result>";
	
	private String resultxml = "<result><success>true</success><accountIdentifier>%s</accountIdentifier></result>";
	
	public String FetchEvent(String token) throws Exception {
		OAuthConsumer consumer = new DefaultOAuthConsumer("jasfengtestapp-7976", "FPBfHMuPPx5nN5Jq");
		//URL url = new URL("https://www.appdirect.com/rest/api/events/"+token);
		URL url = new URL("https://www.appdirect.com/rest/api/events/dummyOrder");
		//URL url = new URL("https://www.appdirect.com/rest/api/events/dummyChange");
		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		consumer.sign(request);
		request.connect();
		Integer responseCode = request.getResponseCode();
		//responseCode.toString();
		
		//String ResponseMessage = request.getResponseMessage();
		if (responseCode == 200) {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(request.getInputStream());
			//return doc.getElementsByTagName("type").item(0).getTextContent();
			return HandleEvent(doc);
		}
		else {
			String message = "HTTP response "+responseCode;
			String result = String.format(ErrorTemplate, "UNKNOWN ERROR", message);
			return result;
		}
		
	}
	
	public String HandleEvent(Document doc) {
		String EventType = doc.getElementsByTagName("type").item(0).getTextContent();
		if (EventType.equals("SUBSCRIPTION_ORDER")) {
			return CreateOrder(doc);
		}
		else if (EventType.equals("SUBSCRIPTION_CHANGE")) {
			return ChangeOrder(doc);
		}
		else{
			String message = "Event type "+EventType+" is not configured";
			String result = String.format(ErrorTemplate, "CONFIGURATION_ERROR", message);
			return result;
		}
		
	}
	
	public String CreateOrder(Document doc) {
		// create companySubscription and appUser objects 
		CompanySubscription companySubscription =  CreateCompanySubscription(doc);
		AppUser appUser = CreateAppUser(doc, companySubscription);
		// bind two objects 
		companySubscription.addAppUser(appUser);
		appUser.setCompanySubscription(companySubscription);
		// persist appUser
		persistCompanySubscription(companySubscription);
		persistAppUser(appUser);
		//String accountId = 
		
		//Query query = em.createQuery("SELECT u FROM com.example.model.CompanySubscription u");
	    //List<CompanySubscription> alist = (List<CompanySubscription>) query.getResultList();
		//return alist.toString() + companySubscription.getEdition();
		//return appUser.toString()+companySubscription.toString();
		
		Integer accountId = companySubscription.getCompanyId();
		return String.format(resultxml, accountId.toString());


	}
	
	public String ChangeOrder(Document doc) {
		// read the incoming xml accountId
		//String accountId = doc.getElementsByTagName("accountIdentifier").item(0).getTextContent();
		Integer accountId = 8;
		// read the incoming xml edition
		String newEdition = doc.getElementsByTagName("editionCode").item(0).getTextContent();
		// get companysubscription by accountId
		CompanySubscription companySubscription = findCompanySubscription(accountId);
		// change the edition
		companySubscription.setEdition(newEdition);
		// persist new companysubscription transaction
		
		return String.format(resultxml, accountId.toString());
		
	}
	
	// create company subscription from xml 
	public CompanySubscription CreateCompanySubscription(Document doc){
		CompanySubscription companySubscription = new CompanySubscription ();
		Element ce = (Element) doc.getElementsByTagName("company").item(0);
		companySubscription.setName(ce.getElementsByTagName("name").item(0).getTextContent());
		companySubscription.setWebsite(ce.getElementsByTagName("website").item(0).getTextContent());
		Element oe = (Element) doc.getElementsByTagName("order").item(0);
		companySubscription.setEdition(oe.getElementsByTagName("editionCode").item(0).getTextContent());
		return companySubscription;
	}
	
	// create appUser from xml 
	public AppUser CreateAppUser(Document doc, CompanySubscription companySubscription) {
		AppUser appUser = new AppUser();
		Element e = (Element) doc.getElementsByTagName("creator").item(0);
		appUser.setEmail(e.getElementsByTagName("email").item(0).getTextContent());
		appUser.setFirstName(e.getElementsByTagName("firstName").item(0).getTextContent());
		appUser.setLastName(e.getElementsByTagName("lastName").item(0).getTextContent());
		appUser.setOpenID(e.getElementsByTagName("openId").item(0).getTextContent());
		return appUser;
	}
	
	public CompanySubscription findCompanySubscription(Integer accountId) {
		 return em.find(CompanySubscription.class, accountId);
	 }
	
	 @Transactional
	 public void persistAppUser(AppUser appUser) {
		 em.persist(appUser);
		 em.flush();
	 }
	 
	 
	 @Transactional
	 public void persistCompanySubscription(CompanySubscription companySubscription) {
		 if (findCompanySubscription(companySubscription.getCompanyId()) == null) {
			 em.persist(companySubscription);
		 }
		 else {
			 em.merge(companySubscription);
		 }
		 em.flush();
		 
	 }
	 
	 
	 
	 
	 
}
