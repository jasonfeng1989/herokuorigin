package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.service.EventService;
import com.example.service.UserService;

@Controller

public class UserController {
	@Autowired
    private UserService userService;
	@RequestMapping("user/")
	@ResponseBody
	public String UserHandler() {
		return userService.createUser();
	}
}
