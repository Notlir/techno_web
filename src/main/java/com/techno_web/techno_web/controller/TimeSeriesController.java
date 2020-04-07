package com.techno_web.techno_web.controller;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.techno_web.techno_web.entities.TimeSeries;
import com.techno_web.techno_web.entities.User;
import com.techno_web.techno_web.entities.UserRight;
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
		
		loSeries.getUsersHasWriteRights().add(loUser);
		
		
		moSeriesService.save(loSeries);
		
		return ResponseEntity.ok().body("OK");
	}
	
	@GetMapping("/getAllSeries")
	public ResponseEntity<String> getAllSeries()
	{
		List<TimeSeries> loSeries= new ArrayList<TimeSeries>();
		String result=" results : ";
		
		try {
			loSeries = moSeriesService.findAll();
		}catch(Exception loE)
		{
			return ResponseEntity.status(500).body("Erreur");
		}
		
		for(TimeSeries loSerie : loSeries)
		{
			for(User loRight : loSerie.getAllUsers())
			{
				result+=loRight.getLogin()+"\n";
			}
			
		}
		
		return ResponseEntity.status(200).body(result);
		
	}
	
	

}
