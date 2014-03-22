package com.example.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openid4java.association.AssociationException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.VerificationResult;
import org.openid4java.discovery.DiscoveryException;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.discovery.Identifier;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.AuthSuccess;
import org.openid4java.message.MessageException;
import org.openid4java.message.ParameterList;
import org.openid4java.message.ax.AxMessage;
import org.openid4java.message.ax.FetchRequest;
import org.openid4java.message.ax.FetchResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

@Controller  
@RequestMapping("openid")
public class SecurityOpenIDController {
	public final ConsumerManager manager = new ConsumerManager();  
	
	@RequestMapping(value="/login")
	public void LoginHandler(@RequestParam("openid") String openid, UriComponentsBuilder builder,
			HttpServletRequest httpReq, HttpServletResponse httpResp) throws Exception {
		String returnUrl = builder.path("/openid/return").build().toUriString();  
		List discoveries = manager.discover(openid);
		DiscoveryInformation discovered = manager.associate(discoveries);
		httpReq.getSession().setAttribute("openid-disc", discovered);
		AuthRequest authReq = manager.authenticate(discovered, returnUrl);  
		 
		FetchRequest fetch = FetchRequest.createFetchRequest();  
		fetch.addAttribute("email", "http://axschema.org/contact/email", true);  
	    fetch.addAttribute("firstName", "http://axschema.org/namePerson/first", true);  
	    fetch.addAttribute("lastName", "http://axschema.org/namePerson/last", true);  
	    authReq.addExtension(fetch);  
	     
		if (!discovered.isVersion2()) {
			httpResp.sendRedirect(authReq.getDestinationUrl(true)); 
		} else {
			httpResp.sendRedirect(authReq.getDestinationUrl(true));  
		 }
	 }
	 
	@RequestMapping("return")
	public String verifyResponse(HttpServletRequest httpReq) throws Exception { 
		ParameterList response = new ParameterList(httpReq.getParameterMap());
		DiscoveryInformation discovered = (DiscoveryInformation) httpReq.getSession().getAttribute("openid-disc");  
		StringBuffer receivingURL = httpReq.getRequestURL();
        String queryString = httpReq.getQueryString();
        if (queryString != null && queryString.length() > 0)
            receivingURL.append("?").append(httpReq.getQueryString());
        VerificationResult verification = manager.verify(receivingURL.toString(),response, discovered);
        Identifier verified = verification.getVerifiedId();  
        if (verified != null) {  
            AuthSuccess authSuccess = (AuthSuccess) verification.getAuthResponse();  
            if (authSuccess.hasExtension(AxMessage.OPENID_NS_AX)) {  
            	FetchResponse fetchResp = (FetchResponse) authSuccess.getExtension(AxMessage.OPENID_NS_AX);  
            	List emails = fetchResp.getAttributeValues("email");  
            	String email = (String) emails.get(0);     
                List lastNames = fetchResp.getAttributeValues("lastName");  
                String lastName = (String) lastNames.get(0);
                List firstNames = fetchResp.getAttributeValues("firstName");  
                String firstName = (String) firstNames.get(0);  
            }  

         }
         return "people";
     
	 }
}
