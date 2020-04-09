package com.techno_web.techno_web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.techno_web.techno_web.services.impl.TimeSeriesServiceImpl;

@Controller
public class TimeSeriesWebController {
	
	@Autowired
	TimeSeriesServiceImpl loServiceImpl;
	
	@GetMapping(path="/getSeriesForMe")
	public String getMySeriesForWeb(Model model,@RequestHeader("Authorization") String token)
	{
		model.addAttribute("time_series", loServiceImpl.findSeriesForMe(token));
		
		return "mySeries";
	}

}
