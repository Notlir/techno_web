package com.techno_web.techno_web.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techno_web.techno_web.entities.Event;
import com.techno_web.techno_web.repositories.EventRepositories;

@Service
public class EventServiceImpl {
	
	@Autowired
	private EventRepositories moRepository;

	public void save(Event poEvent)
	{
		moRepository.save(poEvent);
	}
	
	public List<Event> getAllevents()
	{
		return moRepository.findAll();
	}

}
