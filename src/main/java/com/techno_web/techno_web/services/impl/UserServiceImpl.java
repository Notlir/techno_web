package com.techno_web.techno_web.services.impl;

import com.techno_web.techno_web.entities.User;
import com.techno_web.techno_web.repositories.UserRepositories;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

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
    
    public User findUserByLoginAndPassword(String psLogin,String psPassword)
    {
    	return moRepository.findByLoginAndPassword(psLogin, psPassword);
    }
    
    public User findUserByEtag(String token)
    {
    	User loUser = moRepository.findByToken(token);
    	Calendar loTimeNow = new GregorianCalendar();
    	if(loTimeNow.compareTo(loUser.getToken_creation())/((3600)*1000)>SESSION_TIMEOUT)
    	{
    		return null;
    	}
    	else
    	{
    		return loUser;
    	}
    }
    
}
