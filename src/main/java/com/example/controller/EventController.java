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
	
	/* request url is http://herokuorigin.herokuapp.com/event/create?url=https://www.appdirect.com/rest/api/events/1234
	 * will read out action = "create"
	 * url = "https://www.appdirect.com/rest/api/events/1234"
	 */
	 @RequestMapping("event/{action}")
	 @ResponseBody
	 public String EventHandler(@PathVariable(value="action") String action, @RequestParam String url) throws Exception {
		 String[] URLParts = url.split("/");
		 String token = URLParts[URLParts.length-1];
		 //return token;
		 return(eventService.FetchEvent(token));
		 
		 //return "url is "+url+"\n cation is "+action+"\n token is "+token;
	 }

}