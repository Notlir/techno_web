package com.techno_web.techno_web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
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

}
