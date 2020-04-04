package com.techno_web.techno_web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techno_web.techno_web.entities.Event;
import com.techno_web.techno_web.services.impl.EventServiceImpl;

@RestController
public class EventController {
	
	@Autowired
	EventServiceImpl moEventService;
	
	SimpleDateFormat moFormater = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	
	
	@RequestMapping("/EventTest")
	public String createEvent()
	{
		Event loEvent = new Event();
		
		loEvent.setValue(123456789d);
		loEvent.setEvent_date(new GregorianCalendar());
		
		try {
			moEventService.save(loEvent);
		}catch(Exception loE)
		{
			return "Pas OK";
		}
		
		
		return "OK";
	}
	
	@RequestMapping("/getAllEvent")
	public String getAllEvent()
	{
		String lsResponse="results : ";
		List<Event> loEvents = new ArrayList<Event>();
		try {
			moEventService.getAllevents();
		}catch(Exception loE)
		{
			return "ça marche pas ";
		}
		
		for(Event loCurretnEvent : loEvents)
		{
			lsResponse+=moFormater.format(loCurretnEvent.getEvent_date().getTime());
			lsResponse+=" -> ";
			lsResponse+=loCurretnEvent.getValue();
		}
		
		return lsResponse;
	}

}
