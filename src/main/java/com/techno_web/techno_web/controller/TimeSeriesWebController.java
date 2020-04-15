package com.techno_web.techno_web.controller;

import com.techno_web.techno_web.dto.TimeSeriesDetailDto;
import com.techno_web.techno_web.dto.TimeSeriesDto;
import com.techno_web.techno_web.entities.TimeSeries;
import com.techno_web.techno_web.entities.User;
import com.techno_web.techno_web.services.impl.UserServiceImpl;
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

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
public class TimeSeriesWebController {
	
	@Autowired
	TimeSeriesServiceImpl loServiceImpl;

	@Autowired
	TimeSeriesServiceImpl moSeriesService;

	@Autowired
	UserServiceImpl moUserService;

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

	@PostMapping(
			path = "/getSeriesForMe/newSeries",
			consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},
			produces = {MediaType.TEXT_HTML_VALUE})
	public String CreateSeries(Model model, @CookieValue("Authorization") String token, String title, String description)
	{
		User loUser = moUserService.findUserByEtag(token);

		if(loUser==null)
		{
			return "Session expirée, merci de vous reconnecter";
		}

		TimeSeries loSeries = new TimeSeries();
		loSeries.setTitle(title);
		loSeries.setComments(description);
		loSeries.setCreation_date(new GregorianCalendar());

		if(loUser.getSeries_with_write_rights()==null)
		{
			loUser.setSeries_with_write_rights(new ArrayList<TimeSeries>());
		}

		loUser.getSeries_with_write_rights().add(loSeries);

		moSeriesService.save(loSeries);
		moUserService.save(loUser);

		return "redirect:/getSeriesForMe";
	}

	@DeleteMapping(
			path="/getSeriesForMe/{id}/deleteSeries",
			consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},
			produces = {MediaType.TEXT_HTML_VALUE})
	public @ResponseBody String deleteSeries(@CookieValue("Authorization") String token, @PathVariable("id") String id)
	{
		moSeriesService.deleteSeries(token, id);

		return "redirect:/getSeriesForMe";
	}

	@PostMapping(path="/getSeriesForMe/{id}/updateSeries",
			consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},
			produces = {MediaType.TEXT_HTML_VALUE})
	public String updateTimeSeries(Model model, @CookieValue("Authorization") String token, @PathVariable("id") String idSeries, TimeSeriesDto poUpdatedTimeSeries)
	{
		moSeriesService.updateTimeSeries(token, idSeries, poUpdatedTimeSeries);

		return "redirect:/getSeries/"+idSeries;
	}
}
