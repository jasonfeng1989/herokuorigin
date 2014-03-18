package com.example.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

@Service
public class EventServiceImpl implements EventService {
	private String ErrorTemplate = "<result>" +
									"<success>false</success>"+
									"<errorCode>%s</errorCode>"+
									"<message>%s</message>"+
									"</result>";
	public String FetchEvent(String token) throws Exception {
		OAuthConsumer consumer = new DefaultOAuthConsumer("test-7940", "UlhALaaoJ20e6caa");
		URL url = new URL("https://www.appdirect.com/AppDirect/rest/api/events/"+token);
		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		consumer.sign(request);
		request.connect();
		int ResponseCode = request.getResponseCode();
		//String ResponseMessage = request.getResponseMessage();
		if (ResponseCode == 200) {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(request.getInputStream());
			return HandleEvent(doc);
		}
		else {
			String message = "HTTP response "+ResponseCode;
			String result = String.format(ErrorTemplate, "UNKNOWN ERROR", message);
			return result;
		}
	}
	
	public String HandleEvent(Document doc) {
		//String EventType = doc.
		return "123";
		
	}
	
}
