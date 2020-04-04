package com.techno_web.techno_web.repositories;

import com.techno_web.techno_web.entities.TimeSeries;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TimeSeriesRepositories extends CrudRepository<TimeSeries, UUID> {
}
