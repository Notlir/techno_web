package com.techno_web.techno_web.services.impl;

import com.techno_web.techno_web.entities.Tag;
import org.springframework.beans.factory.annotation.Autowired;

public class TagServiceImpl {

    @Autowired
    private TagRepositories moRepository;

    public void save(Tag poTag)
    {
        moRepository.save(poTag);
    }
}
