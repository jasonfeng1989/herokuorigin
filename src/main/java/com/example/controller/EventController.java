package com.example.controller;

import java.io.IOException;
import java.util.Map;

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
		
	 @RequestMapping("event/{type}")
	 @ResponseBody
	 public String EventHandler(@PathVariable(value="type") String type, @RequestParam String url) {
		 String[] URLParts = url.split("/");
		 String token = URLParts[URLParts.length-1];
		 return "url is "+url+" type is "+type+"token is "+token;
	 }
	 
}