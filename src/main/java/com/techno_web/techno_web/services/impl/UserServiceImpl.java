package com.techno_web.techno_web.services.impl;

import com.techno_web.techno_web.entities.User;
import com.techno_web.techno_web.exceptions.ConflictException;
import com.techno_web.techno_web.exceptions.UnauthorizedException;
import com.techno_web.techno_web.repositories.UserRepositories;
import com.techno_web.techno_web.wrapper.LoginWrapper;
import com.techno_web.techno_web.wrapper.TokenWrapper;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerErrorException;

@Service
public class UserServiceImpl {
	
	private static final Integer SESSION_TIMEOUT = 60;
	
    @Autowired
    private UserRepositories moRepository;

    public User save(User poUser)
    {
       return moRepository.save(poUser);
    }
    
    public User findByLogin(String psLogin)
    {
    	return moRepository.findByLogin(psLogin);
    }
    
    public User findById(String psId)
    {
    	return moRepository.findById(UUID.fromString(psId)).get();
    }
    
    public User findUserByLoginAndPassword(String psLogin,String psPassword)
    {
    	return moRepository.findByLoginAndPassword(psLogin, psPassword);
    }
    
    public User findUserByEtag(String token)
    {
    	User loUser = moRepository.findByToken(token);
    	if(loUser==null)
    	{
    		throw new UnauthorizedException("Vous n'etes pas identifié, merci de vous connecter");
    	}
    	Calendar loTimeNow = new GregorianCalendar();
    	if((loTimeNow.getTimeInMillis() - loUser.getToken_creation().getTimeInMillis())/((60)*1000)>SESSION_TIMEOUT)
    	{
    		return null;
    	}
    	else
    	{
    		return loUser;
    	}
    }
    
    public String doLogin(LoginWrapper poLogin)
    {
    	User loUser=null;
		try {
			
			loUser = findUserByLoginAndPassword(poLogin.getLogin(), poLogin.getPassword());
			
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
		
		
		try {
			
			loUser = save(loUser);
			
			
		}catch(Exception loE)
		{
			System.out.println(loE);
			throw new ServerErrorException("Erreur lors de l'identification", loE);
		}
		
		return loUser.getToken();
    }
    
    
    public User doSignUp(LoginWrapper poLogin)
    {
    	if(findByLogin(poLogin.getLogin())!= null)
    	{
    		throw new ConflictException();
    	}
    	
    	User loUser = new User();
    	loUser.setLogin(poLogin.getLogin());
    	//TODO salt the password
    	loUser.setPassword(poLogin.getPassword());
    	loUser.setSalt(" ");
    	
    	return save(loUser);
    
    }
    
    public void doLogout(String token)
    {

    	User loUser = findUserByEtag(token);
    	
    	if(loUser==null)
    	{
    		throw new UnauthorizedException();
    	}
    	
    	loUser.setToken(null);
    	loUser.setToken_creation(null);
    	save(loUser);
    	

    }
    
}
