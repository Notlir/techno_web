package com.techno_web.techno_web.controller;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.techno_web.techno_web.dto.TimeSeriesDetailDto;
import com.techno_web.techno_web.dto.TimeSeriesDto;
import com.techno_web.techno_web.entities.TimeSeries;
import com.techno_web.techno_web.entities.User;
import com.techno_web.techno_web.services.impl.EventServiceImpl;
import com.techno_web.techno_web.services.impl.TimeSeriesServiceImpl;
import com.techno_web.techno_web.services.impl.UserServiceImpl;

@RestController
public class TimeSeriesController {
	
	@Autowired
	EventServiceImpl moEventService;
	
	@Autowired
	TimeSeriesServiceImpl moSeriesService;
	
	@Autowired
	UserServiceImpl moUserService;
	
	@PostMapping("/seriesCreation")
	public ResponseEntity<String> CreateSeries(@RequestBody TimeSeriesDto poSerie,@RequestHeader("Authorization") String token)
	{
		moSeriesService.createSeries(token, poSerie);
		
		return ResponseEntity.ok().build();
	}
	
	
	
	
	@GetMapping(path="/getSeriesForMe",
			produces = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<TimeSeriesDto>> getMySeries(@RequestHeader("Authorization") String token)
	{
		CacheControl cacheControl = CacheControl.maxAge(30, TimeUnit.SECONDS);
		return ResponseEntity.ok().cacheControl(cacheControl).body(moSeriesService.findSeriesForMe(token));
	}
	
	@GetMapping(path="/getSeries/{id}",
			produces= {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<TimeSeriesDetailDto> getSeries(@RequestHeader("Authorization") String token, @PathVariable("id") String id)
	{
		CacheControl cacheControl = CacheControl.maxAge(30, TimeUnit.SECONDS);
		return ResponseEntity.ok().cacheControl(cacheControl).body(moSeriesService.getTimeSeriesDetail(id, token));
	}
	
	
	@PutMapping(path="/updateSeries/{id}",
			consumes= {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<String> updateTimeSeries(@RequestHeader("Authorization") String token, @PathVariable("id") String id, @RequestBody TimeSeriesDto poUpdatedTimeSeries)
	{
		moSeriesService.updateTimeSeries(token, id, poUpdatedTimeSeries);
		
		return ResponseEntity.ok().build();
	}
	
	@PutMapping(path="/giveRightToUsers/{id}")
	public ResponseEntity<String> giveAccessRightToUser(@RequestHeader("Authorization") String token, @PathVariable("id") String id,@RequestParam("to") String givenUSerId,@RequestParam("writeRight") boolean writeRight)
	{
		moSeriesService.giveRightToUser(token, id, givenUSerId, writeRight);
		
		return ResponseEntity.ok().build();
	}
	
	
	@DeleteMapping(path="/series/{id}/deleteSeries")
	public ResponseEntity<String> deleteSeries(@RequestHeader("Authorization") String token,@PathVariable("id") String id)
	{
		moSeriesService.deleteSeries(token, id);
		
		return ResponseEntity.ok().build();
	}

}
