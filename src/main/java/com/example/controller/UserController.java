package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.model.Person;
import com.example.model.User;
import com.example.service.UserService;

import java.util.Map;

@Controller
public class UserController {
	@Autowired
    private UserService userService;
	
	@RequestMapping("user")
    public String listPeople(Map<String, Object> map) {

        map.put("user", new Person());
        map.put("usersList", userService.listUsers());

        return "user";
    }
}
