package com.techno_web.techno_web.repositories;

import com.techno_web.techno_web.entities.Tag;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TagRepositories extends CrudRepository<Tag, UUID> {
}
