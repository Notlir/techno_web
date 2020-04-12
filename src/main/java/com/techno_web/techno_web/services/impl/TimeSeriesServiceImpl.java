package com.techno_web.techno_web.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techno_web.techno_web.dto.EventDto;
import com.techno_web.techno_web.dto.TimeSeriesDetailDto;
import com.techno_web.techno_web.dto.TimeSeriesDto;
import com.techno_web.techno_web.entities.Event;
import com.techno_web.techno_web.entities.TimeSeries;
import com.techno_web.techno_web.entities.User;
import com.techno_web.techno_web.exceptions.NotFoundException;
import com.techno_web.techno_web.exceptions.UnauthorizedException;
import com.techno_web.techno_web.exceptions.UnprocessableException;
import com.techno_web.techno_web.repositories.TimeSeriesRepositories;



@Service
public class TimeSeriesServiceImpl {

    @Autowired
    private TimeSeriesRepositories moRepository;
    
    @Autowired
    private UserServiceImpl moUserService;
    
    @Autowired
    private EventServiceImpl moEventService;

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
    
    public TimeSeriesDetailDto getTimeSeriesDetail(String id, String token)
    {
    	User loUser = moUserService.findUserByEtag(token);
    	
    	TimeSeries loTimeSeries = findById(id);
    	
    	TimeSeriesDetailDto loSeriesDetails =null;
    	
    	ArrayList<EventDto> loEventList = new ArrayList<EventDto>() ;
    	
    	
    	
    	if(loTimeSeries!=null)
    	{
    		loSeriesDetails= new TimeSeriesDetailDto();
    		loSeriesDetails.setId(loTimeSeries.getId().toString());
    		loSeriesDetails.setTitle(loTimeSeries.getTitle());
    		loSeriesDetails.setDescription(loTimeSeries.getComments());
    		loSeriesDetails.setHasModificationRight(loUser.findRightForTimeSeries(loTimeSeries));
    		for(Event loEvent : loTimeSeries.getEventList())
    		{
    			EventDto loEventDto = new EventDto();
    			loEventDto.setId(loEvent.getId().toString());
    			loEventDto.setTime(loEvent.getEvent_date());
    			loEventDto.setValue(loEvent.getValue());
    			loEventDto.setComment(loEvent.getComments());
    			loEventDto.setTags(loEvent.getTag());
    			loEventList.add(loEventDto);
    		}
    		
    		loSeriesDetails.setEventList(loEventList);
    	}
    	
    	return loSeriesDetails;
    	
    }
    
    public void updateTimeSeries(String token, String id, TimeSeriesDto timeSeriesUpdated)
    {
    	User loUser = moUserService.findUserByEtag(token);
    	
    	TimeSeries loTimeSeries = findById(id);
    	
    	if(!loUser.findRightForTimeSeries(loTimeSeries))
    	{
    		throw new UnauthorizedException("The user do not have the right to alter this time series");
    	}
    	
    	if(timeSeriesUpdated.getTitle()==null || timeSeriesUpdated.getTitle().isEmpty())
    	{
    		throw new UnprocessableException("Title cannot be null !");
    	}
    	
    	loTimeSeries.setTitle(timeSeriesUpdated.getTitle());
    	loTimeSeries.setComments(timeSeriesUpdated.getDescription());
 
    	save(loTimeSeries);
    	
    }
    
    public void giveRightToUser(String token, String seriesId, String Userid, boolean writeRight)
    {
    	User loUser = moUserService.findUserByEtag(token);
    	
    	User loGivenUser = moUserService.findById(Userid);
    	
    	TimeSeries loTimeSeries = findById(seriesId);
    	
    	if(!loUser.findRightForTimeSeries(loTimeSeries))
    	{
    		throw new UnauthorizedException("The user do not have the right to grant right on this series");
    	}
    	
    	loGivenUser.addSeriesWithRight(loTimeSeries, writeRight);
    	
    	moUserService.save(loGivenUser);
    	
    }
    
    @Transactional
    public void deleteSeries(String token,String id)
    {
    	User loUser = moUserService.findUserByEtag(token);
    	
    	TimeSeries loTimeSeries = findById(id);
    	
    	if(!loUser.findRightForTimeSeries(loTimeSeries))
    	{
    		throw new UnauthorizedException("This user does not have the right to delete this series");
    	}  	
    	
    	List<User> loUsersWithSeries = moUserService.findByTimeSeries(loTimeSeries);
    	
    	for(User loUsers : loUsersWithSeries)
    	{
    		loUsers.removeTimeSeries(loTimeSeries);
    	}
    	
    	moUserService.saveAll(loUsersWithSeries);
    	
    	moEventService.deleteAllEventForTimeSeries(loTimeSeries);
    	
    	moRepository.delete(loTimeSeries);
    	
    }
	
}
