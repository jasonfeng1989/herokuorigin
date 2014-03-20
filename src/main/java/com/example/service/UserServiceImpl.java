package com.example.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.Person;
import com.example.model.User;

@Service
public class UserServiceImpl implements UserService {
	@PersistenceContext
    EntityManager em;
	
	public String createUser() {
		User user = new User();
		user.setUserId(3);
		user.setEmail("abc");
		user.setFirstName("jason123");
		user.setLastName("feng123");
		user.setOpenID("helloworld");
		addUser(user);
		return "success";
	}
	
	@Transactional
    public void addUser(User user) {
        em.persist(user);
        //em.flush();
    }
}
