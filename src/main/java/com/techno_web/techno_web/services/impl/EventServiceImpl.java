package com.techno_web.techno_web.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techno_web.techno_web.dto.EventDto;
import com.techno_web.techno_web.entities.Event;
import com.techno_web.techno_web.entities.TimeSeries;
import com.techno_web.techno_web.entities.User;
import com.techno_web.techno_web.exceptions.UnauthorizedException;
import com.techno_web.techno_web.repositories.EventRepositories;

@Service
public class EventServiceImpl {
	
	@Autowired
	private EventRepositories moRepository;
	
	@Autowired
	private UserServiceImpl moUserService;
	
	@Autowired
	private TimeSeriesServiceImpl moTimeSeriesService;
	
	

	public Event save(Event poEvent)
	{
		return moRepository.save(poEvent);
	}
	
	public List<Event> getAllevents()
	{
		return moRepository.findAll();
	}
	
	public void createNewEvent(EventDto loDto,String token,String id)
	{
		User loUser=moUserService.findUserByEtag(token);
		
		TimeSeries loTimeSeries = moTimeSeriesService.findById(id);
		
		if(!loUser.findRightForTimeSeries(loTimeSeries))
		{
			throw new UnauthorizedException("this user has no rights to add new events on this series");
		}
		
		Event loNewEvent = new Event();
		loNewEvent.setEvent_date(loDto.getTime());
		loNewEvent.setValue(loDto.getValue());
		loNewEvent.setComments(loDto.getComment());
		loNewEvent.setTag(loDto.getTags());
		
		loNewEvent =save(loNewEvent);
		
		loTimeSeries.getEventList().add(loNewEvent);
		moTimeSeriesService.save(loTimeSeries);
		
	}

}
