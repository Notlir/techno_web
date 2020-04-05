package com.techno_web.techno_web.services.impl;

import com.techno_web.techno_web.entities.Tag;
import com.techno_web.techno_web.repositories.TagRepositories;
import org.springframework.beans.factory.annotation.Autowired;

public class TagServiceImpl {

    @Autowired
    private TagRepositories moRepository;

    public void save(Tag poTag)
    {
        moRepository.save(poTag);
    }
}
