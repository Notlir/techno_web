package com.techno_web.techno_web.repositories;

import com.techno_web.techno_web.entities.TimeSeries;
import com.techno_web.techno_web.entities.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface UserRepositories extends CrudRepository<User, UUID> {
	
	User findByLogin(String login);

	@Override
	List<User> findAll();
	
	User findByLoginAndPassword(String login,String password);
	
	User findByToken(String token);
	
	@Query("From User u join u.series_with_write_rights w where :timeSeries in w")
	List<User> findUsersBySeriesWithWrite(@Param("timeSeries")TimeSeries poTimeSeries);
	
	@Query("From User u join u.series_with_read_rights w where :timeSeries in w")
	List<User> findUsersBySeriesWithRead(@Param("timeSeries")TimeSeries poTimeSeries);
	
}
