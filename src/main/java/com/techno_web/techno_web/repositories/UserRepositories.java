package com.techno_web.techno_web.repositories;

import com.techno_web.techno_web.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserRepositories extends CrudRepository<User, UUID> {
	
	User findByLogin(String login);
	
	User findByLoginAndPassword(String login,String password);
	
	User findByToken(String token);
	
}
