package com.techno_web.techno_web.services.impl;

import com.techno_web.techno_web.entities.TimeSeries;
import com.techno_web.techno_web.entities.User;
import com.techno_web.techno_web.exceptions.ConflictException;
import com.techno_web.techno_web.exceptions.NotFoundException;
import com.techno_web.techno_web.exceptions.UnauthorizedException;
import com.techno_web.techno_web.repositories.UserRepositories;
import com.techno_web.techno_web.wrapper.LoginWrapper;
import com.techno_web.techno_web.wrapper.TokenWrapper;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.apache.tomcat.util.codec.binary.StringUtils;
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

	public List<User> findAll()
	{
		return moRepository.findAll();
	}
    
    public User findByLogin(String psLogin)
    {
    	return moRepository.findByLogin(psLogin);
    }
    
    public User findById(String psId)
    {
    	Optional<User> loUser =  moRepository.findById(UUID.fromString(psId));
    	if(!loUser.isPresent())
    	{
    		throw new NotFoundException("User not found");
    	}
    	
    	return loUser.get();
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
    		throw new UnauthorizedException("Token Expired");
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
			
			loUser = findByLogin(poLogin.getLogin());
			
		}catch(Exception loE)
		{
			throw new ServerErrorException("Erreur lors de l'identification", loE);
			
		}
		
		if(loUser == null)
		{
			throw new UnauthorizedException();
		}
    	
    	byte[] salt=Base64.getDecoder().decode(loUser.getSalt());
    	
    	byte[] hash =null;
    	
    	KeySpec spec = new PBEKeySpec(poLogin.getPassword().toCharArray(), salt, 65536, 128);
    	try {
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			hash = factory.generateSecret(spec).getEncoded();
		} catch (NoSuchAlgorithmException e) {
			System.out.println(e.getLocalizedMessage());
		} catch (InvalidKeySpecException e) {
			System.out.println(e.getLocalizedMessage());
		}
    	
    	String lsSubmittedPassword = Base64.getEncoder().encodeToString(hash);
		
		if(!lsSubmittedPassword.equals(loUser.getPassword()))
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
    	//salt the password
    	SecureRandom random = new SecureRandom();
    	byte[] salt = new byte[16];
    	random.nextBytes(salt);
    	
    	byte[] hash =null;
    	
    	KeySpec spec = new PBEKeySpec(poLogin.getPassword().toCharArray(), salt, 65536, 128);
    	try {
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			hash = factory.generateSecret(spec).getEncoded();
		} catch (NoSuchAlgorithmException e) {
			
			System.out.println(e.getLocalizedMessage());
			
		} catch (InvalidKeySpecException e) {
			System.out.println(e.getLocalizedMessage());
		}
    	
    	loUser.setPassword(Base64.getEncoder().encodeToString(hash));
    	loUser.setSalt(Base64.getEncoder().encodeToString(salt));
    	
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
    
    public List<User> findByTimeSeries(TimeSeries poTimeSeries)
    {
    	List<User> loUsers= moRepository.findUsersBySeriesWithWrite(poTimeSeries);
    	loUsers.addAll(moRepository.findUsersBySeriesWithRead(poTimeSeries));
    	
    	return loUsers;
    }
    
    public void saveAll(List<User> poUsers)
    {
    	moRepository.saveAll(poUsers);
    }
    
}
