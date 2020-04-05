package com.techno_web.techno_web.controller;

import java.util.GregorianCalendar;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techno_web.techno_web.entities.User;
import com.techno_web.techno_web.services.impl.UserServiceImpl;

@RestController
public class UserController {
	
	private static final String LOGIN_KEY="login";
	
	private static final String PASSSWORD_KEY="password";
	
	
	@Autowired
	UserServiceImpl moUserService;
	
	
	@RequestMapping("/createDefaultUser")
	public String createDefaultUser()
	{
		User loUser = new User();
		
		loUser.setLogin("Denis");
		loUser.setPassword("aled");
		loUser.setSalt("123");

		
		try {
			moUserService.save(loUser);
		}catch(Exception loE)
		{
			return "ça marche pas";
		}
		
		return "OK";
	}
	
	@RequestMapping("/getUser/{login}")
	public String getUserByLogin(@PathVariable String login)
	{
		String result = "";
		User loUser;
		
		try {
			
			loUser = moUserService.findByLogin(login);
		}catch(Exception loE)
		{
			return "ça marche pas";
		}
		
		result+= loUser.getId() +" "+loUser.getLogin();
		
		return result;
	}
	
	@PostMapping(
			path="/login",
			consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public ResponseEntity<String> login(@RequestParam MultiValueMap<String,String> paramMap)
	{
		User loUser=null;
		try {
			
			loUser = moUserService.findUserByLoginAndPassword(paramMap.get(LOGIN_KEY).get(0), paramMap.get(PASSSWORD_KEY).get(0));
			
		}catch(Exception loE)
		{
			System.out.println(loE.getMessage());
			return ResponseEntity.status(500).body("errueur lors de l'identification");
			
		}
		
		if(loUser == null)
		{
			return ResponseEntity.status(401).body("indentifiant ou mot de passe incorect");
		}
		
		
		UUID loEtag = UUID.randomUUID();
		loUser.setToken(loEtag.toString());
		loUser.setToken_creation(new GregorianCalendar());
		
		try {
			
			loUser = moUserService.save(loUser);
			
		}catch(Exception loE)
		{
			System.out.println(loE.getMessage());
			return ResponseEntity.status(500).body("errueur lors de l'identification");
		}
		
		return ResponseEntity.ok().eTag(loEtag.toString()).body("Bienvenue !");
	}
	
}
