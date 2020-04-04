package com.techno_web.techno_web.repositories;

import org.springframework.data.repository.CrudRepository;

import com.techno_web.techno_web.entities.Event;

import java.util.UUID;

public interface EventRepositories extends CrudRepository<Event, UUID> {
}
