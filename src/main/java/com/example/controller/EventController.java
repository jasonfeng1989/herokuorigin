package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EventController {
	
	 @RequestMapping("event/*https://www.acme-marketplace.com/api/integration/v1/events/{token}")
	 @ResponseBody
	 public String EventHandler(@PathVariable("token") String token) {
		 //System.out.println("token is "+token);
		 return "token is ";
	 }
	 
}