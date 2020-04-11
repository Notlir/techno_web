package com.techno_web.techno_web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {
	
	public UnauthorizedException(){
		super("login ou mot de passe incorrect");
	}
	
	public UnauthorizedException(String message)
	{
		super(message);
	}
}
