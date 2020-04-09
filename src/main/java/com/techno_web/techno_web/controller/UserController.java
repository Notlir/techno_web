package com.techno_web.techno_web.controller;

import java.util.GregorianCalendar;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerErrorException;

import com.techno_web.techno_web.entities.User;
import com.techno_web.techno_web.exceptions.UnauthorizedException;
import com.techno_web.techno_web.services.impl.UserServiceImpl;
import com.techno_web.techno_web.wrapper.LoginWrapper;
import com.techno_web.techno_web.wrapper.TokenWrapper;

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
			consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},
			produces= {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody TokenWrapper login(@RequestBody LoginWrapper poParams)
	{
		User loUser=null;
		try {
			
			loUser = moUserService.findUserByLoginAndPassword(poParams.getLogin(), poParams.getPassword());
			
		}catch(Exception loE)
		{
			System.out.println(loE);
			throw new ServerErrorException("Erreur lors de l'identification", loE);
			
		}
		
		if(loUser == null)
		{
			throw new UnauthorizedException();
		}
		
		
		UUID loToken = UUID.randomUUID();
		loUser.setToken(loToken.toString());
		loUser.setToken_creation(new GregorianCalendar());
		
		TokenWrapper loResponse = new TokenWrapper();
		loResponse.setToken(loToken.toString());
		
		try {
			
			loUser = moUserService.save(loUser);
			
			
		}catch(Exception loE)
		{
			System.out.println(loE);
			throw new ServerErrorException("Erreur lors de l'identification", loE);
		}
		
		return loResponse;
	}
	
	
	
	
}
