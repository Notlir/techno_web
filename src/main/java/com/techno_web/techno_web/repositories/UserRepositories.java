package com.techno_web.techno_web.repositories;

import com.techno_web.techno_web.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepositories extends CrudRepository<User,Integer> {
}
