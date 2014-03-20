package com.example.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.Person;
import com.example.model.AppUser;

@Service
public class UserServiceImpl implements UserService {
	@PersistenceContext
    EntityManager em;
	
	public String createUser() {
		
		addUser();
		return "success";
	}
	
	@Transactional
    public void addUser() {
		AppUser appUser = new AppUser();
		appUser.setUserId(3);
		appUser.setEmail("abc");
		appUser.setFirstName("jason123");
		appUser.setLastName("feng123");
		appUser.setOpenID("helloworld");
        em.persist(appUser);
        em.flush();
    }
}
