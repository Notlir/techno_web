package com.techno_web.techno_web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.techno_web.techno_web.dto.EventDto;
import com.techno_web.techno_web.entities.Event;
import com.techno_web.techno_web.services.impl.EventServiceImpl;

@RestController
public class EventController {
	
	@Autowired
	EventServiceImpl moEventService;
		
	SimpleDateFormat moFormaterPrecise = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	
	
	@PostMapping(path="/series/{id}/newEvent")
	public ResponseEntity<String> createNewEvent(@RequestHeader("Authorization") String token,@RequestBody EventDto poNewEvent,@PathVariable("id") String id)
	{
		moEventService.createNewEvent(poNewEvent, token, id);
		
		return ResponseEntity.ok().build();
		
	}
	
	@PutMapping(path="/series/{id_series}/updateEvent/{id_event}",
			consumes= {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<String> updateEvent(@RequestHeader("Authorization") String token,@RequestBody EventDto event, @PathVariable("id_series") String idSeries, @PathVariable("id_event")String id_event )
	{
		moEventService.updateEvent(token, idSeries, id_event, event);
		
		return ResponseEntity.ok().build();
	}
	
	
	@DeleteMapping(path="/series/{id_series}/deleteEvent/{id_event}")
	public ResponseEntity<String> deleteEvent(@RequestHeader("Authorization") String token,@PathVariable("id_series") String id_series, @PathVariable("id_event")String id_event)
	{
		moEventService.deleteEvent(token, id_series, id_event);
		
		return ResponseEntity.ok().build();
	}
	
	@GetMapping(path="events/findByTag",
			produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<List<EventDto>> findEventByTag(@RequestHeader("Authorization") String token,@RequestParam("tag")String tag)
	{
		CacheControl cacheControl = CacheControl.maxAge(10, TimeUnit.SECONDS);
		return ResponseEntity.ok().cacheControl(cacheControl).body(moEventService.findEventByTag(token, tag));
		
	}
	
	@GetMapping (path = "events/tagFrequencies",
			produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<Integer> findEventByTagsAndDate(@RequestHeader("Authorization") String token,@RequestParam("tag")String tag,@RequestParam("dateFrom")String dateFrom,@RequestParam("dateTo")String dateTo)
	{
		CacheControl cacheControl = CacheControl.maxAge(10, TimeUnit.SECONDS);
		return ResponseEntity.ok().cacheControl(cacheControl).body(moEventService.findTagFrequency(token, tag, dateFrom, dateTo));
	}
	
	@GetMapping(path = "events/timeSinceLastTag")
	public ResponseEntity<Long> findTimeSinceLastTag(@RequestHeader("Authorization") String token,@RequestParam("tag")String tag)
	{
		CacheControl cacheControl = CacheControl.maxAge(10, TimeUnit.SECONDS);
		return ResponseEntity.ok().cacheControl(cacheControl).body(moEventService.findTimeSinceLastTag(token, tag));
	}

}
