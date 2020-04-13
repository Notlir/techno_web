package com.techno_web.techno_web.services.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techno_web.techno_web.dto.EventDto;
import com.techno_web.techno_web.entities.Event;
import com.techno_web.techno_web.entities.TimeSeries;
import com.techno_web.techno_web.entities.User;
import com.techno_web.techno_web.exceptions.NotFoundException;
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
		Optional<Event> loEvent =  this.moRepository.findById(UUID.fromString(Id));
		if(!loEvent.isPresent())
		{
			throw new NotFoundException("This event does not exists");
		}
		else
		{
			return loEvent.get();
		}
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
		
		//necessary to save to delete all events constraints in db
		moTimeSeriesService.save(poTimeSeries);
		
		moRepository.deleteAll(loEvents);
		
	}
	
	public List<EventDto> findEventByTag(String token, String tag)
	{
		User loUser = moUserService.findUserByEtag(token);
		
		ArrayList<EventDto> loEvents = new ArrayList<>();
		
		loEvents.addAll(prepareEventDtoForTags(loUser.getSeries_with_write_rights(), true, tag));
		
		loEvents.addAll(prepareEventDtoForTags(loUser.getSeries_with_read_rights(), false, tag));
		
		return loEvents;
		
	}
	
	private List<EventDto> prepareEventDtoForTags(List<TimeSeries> poList, boolean isEditable,String tag)
	{
		ArrayList<EventDto> loEvents = new ArrayList<>();
		
		for(TimeSeries loSeries : poList)
		{
			for(Event loEvent : loSeries.getEventList())
			{
				if(loEvent.getTag().contains(tag))
				{
					EventDto loEventDto = new EventDto();
					loEventDto.setTime(loEvent.getEvent_date());
					loEventDto.setValue(loEvent.getValue());
					loEventDto.setComment(loEvent.getComments());
					loEventDto.setId(loEvent.getId().toString());
					loEventDto.setEditable(isEditable);
					loEventDto.setTags(loEvent.getTag());
					loEvents.add(loEventDto);
				}
			}
		}
		
		return loEvents;
		
	}
	
	public Integer findTagFrequency(String token, String tag, String dateFrom, String dateTo)
	{
		SimpleDateFormat loFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		
		Calendar loDateFrom = new GregorianCalendar();
		Calendar loDateTo = new GregorianCalendar();
		
		Integer lnFrequency=0;
		
		try {
		loDateFrom.setTime(loFormatter.parse(dateFrom));
		loDateTo.setTime(loFormatter.parse(dateTo));
		}catch(ParseException loE)
		{
			throw new UnprocessableException("dates are not in correct format");
		}
		
		List<EventDto> loEvents = findEventByTag(token, tag);
		
		for(EventDto loEvent : loEvents)
		{
			if(loDateFrom.before(loEvent.getTime()) && loDateTo.after(loEvent.getTime()))
			{
				lnFrequency++;
			}
		}
		
		return lnFrequency;
	}
	
	
	public Long findTimeSinceLastTag(String token, String tag)
	{
		List<EventDto> loEvents = findEventByTag(token, tag);
		
		if(loEvents == null || loEvents.isEmpty())
		{
			return 0l;
		}
		
		EventDto mostRecentEvent = loEvents.get(0);
		
		for(EventDto loEvent : loEvents)
		{
			if(loEvent.getTime().after(mostRecentEvent))
			{
				mostRecentEvent = loEvent;
			}
		}
		
		GregorianCalendar loNow = new GregorianCalendar();
		
		return loNow.getTimeInMillis()-mostRecentEvent.getTime().getTimeInMillis();
		
	}

}
