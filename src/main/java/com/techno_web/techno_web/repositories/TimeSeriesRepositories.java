package com.techno_web.techno_web.repositories;

import com.techno_web.techno_web.entities.TimeSeries;
import com.techno_web.techno_web.entities.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import java.util.List;
import java.util.UUID;

public interface TimeSeriesRepositories extends CrudRepository<TimeSeries, UUID> {
	
	
	List<TimeSeries> findAll();
	
	
	//List<TimeSeries> findByUsersHasWriteRightsContainingOrUsersHasReadRightsContaining(User user,User user2);

	
}
