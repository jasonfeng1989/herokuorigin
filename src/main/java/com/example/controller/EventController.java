package com.example.controller;

import com.example.service.EventService;
import com.example.service.PersonService;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	 public String EventHandler(@PathVariable(value="action") String action, @RequestParam String url) {
		 String[] URLParts = url.split("/");
		 String token = URLParts[URLParts.length-1];
		 //eventService.FetchEvent(token);
		 return "url is "+url+"\n cation is "+action+"\n token is "+token;
	 }
	 
}