package com.techno_web.techno_web.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techno_web.techno_web.dto.EventDto;
import com.techno_web.techno_web.entities.Event;
import com.techno_web.techno_web.entities.TimeSeries;
import com.techno_web.techno_web.entities.User;
import com.techno_web.techno_web.exceptions.UnauthorizedException;
import com.techno_web.techno_web.exceptions.UnprocessableException;
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
	
	public Event findById(String Id)
	{
		return this.moRepository.findById(UUID.fromString(Id)).get();
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
	
	public void updateEvent(String token, String seriesid, String eventId,EventDto loEventUpdated)
	{
		User loUser = moUserService.findUserByEtag(token);
		
		TimeSeries loTimeSeries = moTimeSeriesService.findById(seriesid);
		
		if(!loUser.findRightForTimeSeries(loTimeSeries))
		{
			throw new UnauthorizedException("This user does not have the right to modify this event");
		}
		
		Event loEvent = findById(eventId);
		
		if(loEventUpdated.getValue()==null || loEventUpdated.getTime()==null)
		{
			throw new UnprocessableException("Value or Date cannot be null for an event");
		}
		
		loEvent.setValue(loEventUpdated.getValue());
		loEvent.setEvent_date(loEventUpdated.getTime());
		loEvent.setComments(loEventUpdated.getComment());
		loEvent.setTag(loEventUpdated.getTags());
		
		save(loEvent);
	}
	
	public void deleteEvent(String token, String id_series, String id_event)
	{
		User loUser = moUserService.findUserByEtag(token);
		
		TimeSeries loTimeSeries = moTimeSeriesService.findById(id_series);
		
		if(!loUser.findRightForTimeSeries(loTimeSeries))
		{
			throw new UnauthorizedException("The user does not have the right to delete events in this series");
		}
		
		Event loEvent = findById(id_event);
		
		loTimeSeries.getEventList().remove(loEvent);
		
		moTimeSeriesService.save(loTimeSeries);
		
		moRepository.delete(loEvent);
	}
	
	public void deleteAllEventForTimeSeries(TimeSeries poTimeSeries)
	{
		List<Event> loEvents = poTimeSeries.getEventList();
		
		poTimeSeries.setEventList(null);
		
		//necessary to save to delete all events constraints in db;
		moTimeSeriesService.save(poTimeSeries);
		
		moRepository.deleteAll(loEvents);
		
	}
	

}
