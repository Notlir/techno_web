package com.techno_web.techno_web.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techno_web.techno_web.dto.TimeSeriesDto;
import com.techno_web.techno_web.entities.TimeSeries;
import com.techno_web.techno_web.entities.User;
import com.techno_web.techno_web.exceptions.NotFoundException;
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
    
    public TimeSeries findById(String id)
    {
    	Optional<TimeSeries> loSeries = moRepository.findById(UUID.fromString(id));
    	if(loSeries==null)
    	{
    		throw new NotFoundException("time series does not exists");
    	}
    	
    	return loSeries.get();
    }
    
    public List<TimeSeriesDto> findSeriesForMe(String token) throws UnauthorizedException
    {
    	User loUser;
    	
		loUser = moUserService.findUserByEtag(token);
		
		if(loUser == null)
		{
			throw new UnauthorizedException("Token Expired");
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
			loResult.add(new TimeSeriesDto(loSerie.getId().toString(),loSerie.getTitle(),loSerie.getComments(),true));
		}
		for (TimeSeries loSerie : poUser.getSeries_with_read_rights())
		{
			loResult.add(new TimeSeriesDto(loSerie.getId().toString(),loSerie.getTitle(),loSerie.getComments(),false));
		}
		
		return loResult;
	}
	
}
