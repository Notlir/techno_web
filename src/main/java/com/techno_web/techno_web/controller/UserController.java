package com.techno_web.techno_web.controller;

import java.util.GregorianCalendar;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techno_web.techno_web.entities.User;
import com.techno_web.techno_web.services.impl.UserServiceImpl;
import com.techno_web.techno_web.wrapper.LoginWrapper;

@RestController
public class UserController {
	
	private static final String LOGIN_KEY="login";
	
	private static final String PASSSWORD_KEY="password";
	
	
	@Autowired
	UserServiceImpl moUserService;
	
	
	
	
	@PostMapping("/createDefaultUser")
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
	
	
	@PostMapping(
			path="/login",
			consumes = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<String> login(@RequestBody LoginWrapper poLoginInfo)
	{
		ResponseEntity<String> response;
		
		Integer status = findUser(poLoginInfo.getLogin(),poLoginInfo.getPassword());
		
		switch(status)
		{
			case 200:
				response = ResponseEntity.ok().body("Bienvenue !");
				break;
			case 401:
				response = ResponseEntity.status(401).body("Utilisateur ou mot de passe incorrect");
				break;
			default :
				response = ResponseEntity.status(500).body("erreur lors de l'identification");
				break;	
		}
		
		return response;
	}
	
	@PostMapping(
			path="/login",
			consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public ResponseEntity<String> login(@RequestParam MultiValueMap<String, String> poParams)
	{
		ResponseEntity<String> response;
		
		Integer status = findUser(poParams.get(LOGIN_KEY).get(0),poParams.get(PASSSWORD_KEY).get(0));
		
		switch(status)
		{
			case 200:
				response = ResponseEntity.ok().body("Bienvenue !");
				break;
			case 401:
				response = ResponseEntity.status(401).body("Utilisateur ou mot de passe incorrect");
				break;
			default :
				response = ResponseEntity.status(500).body("erreur lors de l'identification");
				break;	
		}
		
		return response;
	}
	
	
	private Integer findUser(String psLogin, String psPassword)
	{
		User loUser=null;
		try {
			
			loUser = moUserService.findUserByLoginAndPassword(psLogin, psPassword);
			
		}catch(Exception loE)
		{
			System.out.println(loE);
			return 500;
			
		}
		
		if(loUser == null)
		{
			return 401;
		}
		
		
		UUID loEtag = UUID.randomUUID();
		loUser.setToken(loEtag.toString());
		loUser.setToken_creation(new GregorianCalendar());
		
		try {
			
			loUser = moUserService.save(loUser);
			
		}catch(Exception loE)
		{
			System.out.println(loE);
			return 500;
		}
		
		return 200;
		
	}
	
}
