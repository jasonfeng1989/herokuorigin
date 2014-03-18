package com.example.controller;

import java.io.IOException;

import com.example.service.EventService;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EventController {
	
	@Autowired
    private EventService eventService;
	
	/* request url is http://herokuorigin.herokuapp.com/event/create?url=https://www.acme-marketplace.com/api/integration/v1/events/1234
	 * will read out action = "create"
	 * url = "https://www.acme-marketplace.com/api/integration/v1/events/1234"
	 */
	 @RequestMapping("event/{action}")
	 @ResponseBody
	 public String EventHandler(@PathVariable(value="action") String action, @RequestParam String url) throws 
	 OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, IOException {
		 String[] URLParts = url.split("/");
		 String token = URLParts[URLParts.length-1];
		 return(eventService.FetchEvent(token));
		 
		 //return "url is "+url+"\n cation is "+action+"\n token is "+token;
	 }
	 
	 /*
	 public String FetchEvent(String token) throws IOException, OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException{
			OAuthConsumer consumer = new DefaultOAuthConsumer("test-7940", "UlhALaaoJ20e6caa");
			URL url = new URL("https://www.appdirect.com/AppDirect/rest/api/events/"+token);
			HttpURLConnection request = (HttpURLConnection) url.openConnection();
			consumer.sign(request);
			request.connect();
			int ResponseCode = request.getResponseCode();
			String ResponseMessage = request.getResponseMessage();
			return ResponseMessage;
	}
	*/
}