package com.techno_web.techno_web.services.impl;

import com.techno_web.techno_web.entities.User;
import com.techno_web.techno_web.repositories.UserRepositories;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl {
    @Autowired
    private UserRepositories moRepository;

    public void save(User poUser)
    {
        moRepository.save(poUser);
    }
}
