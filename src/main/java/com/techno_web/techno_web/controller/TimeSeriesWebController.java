package com.techno_web.techno_web.controller;

import com.techno_web.techno_web.dto.TimeSeriesDetailDto;
import com.techno_web.techno_web.dto.TimeSeriesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.techno_web.techno_web.services.impl.TimeSeriesServiceImpl;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
public class TimeSeriesWebController {
	
	@Autowired
	TimeSeriesServiceImpl loServiceImpl;

	@Autowired
	TimeSeriesServiceImpl moSeriesService;

	@GetMapping(
			path = "/getSeriesForMe",
			produces = {MediaType.TEXT_HTML_VALUE})
	public String getMySeriesForWeb(Model model, @CookieValue("Authorization") String token,HttpServletResponse poResponse)
	{
		List<TimeSeriesDto> list = moSeriesService.findSeriesForMe(token);
		model.addAttribute("list", list);
		poResponse.addHeader("Cache-Control", "max-age=30");
		return "mySeries";
	}

	@GetMapping(path="/getSeries/{id}",
			produces = {MediaType.TEXT_HTML_VALUE})
	public String getSeries(Model model, @CookieValue("Authorization") String token, @PathVariable("id") String id,HttpServletResponse poResponse)
	{
		TimeSeriesDetailDto serie = moSeriesService.getTimeSeriesDetail(id, token);
		model.addAttribute("serie", serie);
		poResponse.addHeader("Cache-Control", "max-age=30");
		return "detailsSeries";
	}

}
