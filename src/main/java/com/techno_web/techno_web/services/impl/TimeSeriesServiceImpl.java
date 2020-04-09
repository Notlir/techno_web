package com.techno_web.techno_web.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techno_web.techno_web.dto.TimeSeriesDto;
import com.techno_web.techno_web.entities.TimeSeries;
import com.techno_web.techno_web.entities.User;
import com.techno_web.techno_web.exceptions.UnauthorizedException;
import com.techno_web.techno_web.repositories.TimeSeriesRepositories;

@Service
public class TimeSeriesServiceImpl {

    @Autowired
    private TimeSeriesRepositories moRepository;
    
    @Autowired
    private UserServiceImpl moUserService;

    public void save(TimeSeries poTimeSeries)
    {
        moRepository.save(poTimeSeries);
    }
    
    public List<TimeSeries> findAll()
    {
    	return moRepository.findAll();
    }
    
    public List<TimeSeriesDto> findSeriesForMe(String token) throws UnauthorizedException
    {
    	User loUser;
		try {
		 loUser = moUserService.findUserByEtag(token);
		}catch(Exception loE)
		{
			throw new UnauthorizedException();
		}
		List<TimeSeriesDto> loResponse= new ArrayList<TimeSeriesDto>();
		try {
		
		loResponse = prepareListFromUser(loUser);
		}catch(Exception loE)
		{
			System.out.println("Erreur ! "+loE.getMessage());
		}
		
		
		return loResponse;
    	
    }
    
    private List<TimeSeriesDto> prepareListFromUser(User poUser)
	{
		ArrayList<TimeSeriesDto> loResult = new ArrayList<TimeSeriesDto>();
		
		for (TimeSeries loSerie : poUser.getSeries_with_write_rights())
		{
			loResult.add(new TimeSeriesDto(loSerie.getTitle(),loSerie.getComments(),true));
		}
		for (TimeSeries loSerie : poUser.getSeries_with_read_rights())
		{
			loResult.add(new TimeSeriesDto(loSerie.getTitle(),loSerie.getComments(),true));
		}
		
		return loResult;
	}
	
}
