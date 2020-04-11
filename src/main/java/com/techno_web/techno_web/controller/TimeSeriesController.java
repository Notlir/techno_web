package com.techno_web.techno_web.controller;

import java.rmi.ServerError;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.techno_web.techno_web.dto.TimeSeriesDetailDto;
import com.techno_web.techno_web.dto.TimeSeriesDto;
import com.techno_web.techno_web.entities.TimeSeries;
import com.techno_web.techno_web.entities.User;
import com.techno_web.techno_web.entities.UserRight;
import com.techno_web.techno_web.exceptions.UnauthorizedException;
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
	public ResponseEntity<String> CreateSeries(@RequestBody String title,@RequestHeader("Authorization") String token)
	{
		User loUser = moUserService.findUserByEtag(token);
		
		if(loUser==null)
		{
			return ResponseEntity.status(401).body("Session expirée, merci de vous reconnecter");
		}
		
		TimeSeries loSeries = new TimeSeries();
		loSeries.setTitle(title);
		loSeries.setCreation_date(new GregorianCalendar());
		
		if(loUser.getSeries_with_write_rights()==null)
		{
			loUser.setSeries_with_write_rights(new ArrayList<TimeSeries>());
		}
		
		loUser.getSeries_with_write_rights().add(loSeries);
		
		moSeriesService.save(loSeries);
		moUserService.save(loUser);
		
		return ResponseEntity.ok().build();
	}
	
	
	
	
	@GetMapping(path="/getSeriesForMe",
			produces = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody List<TimeSeriesDto> getMySeries(@RequestHeader("Authorization") String token)
	{
		return moSeriesService.findSeriesForMe(token);
	}
	
	@GetMapping(path="/getSeries/{id}",
			produces= {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE,MediaType.TEXT_PLAIN_VALUE})
	public @ResponseBody TimeSeriesDetailDto getSeries(@RequestHeader("Authorization") String token, @PathVariable("id") String id)
	{
		
		return null;
	}

}
