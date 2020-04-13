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
import org.springframework.web.bind.annotation.RequestHeader;
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
		
		TokenWrapper loResponse = new TokenWrapper();
		loResponse.setToken(moUserService.doLogin(poParams));
		
		return loResponse;
	}
	
	@PostMapping(
			path="/doSignUp",
			consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE}
			)
	public ResponseEntity<String> signUp(@RequestBody LoginWrapper poParams)
	{
		if(moUserService.doSignUp(poParams)!=null)
		{
			return ResponseEntity.ok().build();
		}
		else
		{
			return ResponseEntity.status(500).body("Impossible créer l'utilisateur");
		}
		
	}
	
	@PostMapping(path="/logout")
	public ResponseEntity<String> logout(@RequestHeader("Authorization") String token)
	{
		moUserService.doLogout(token);
		
		return ResponseEntity.ok().build();
	}
	
	
	
	
	
}
