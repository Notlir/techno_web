package com.techno_web.techno_web.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techno_web.techno_web.entities.TimeSeries;
import com.techno_web.techno_web.repositories.TimeSeriesRepositories;

@Service
public class TimeSeriesServiceImpl {

    @Autowired
    private TimeSeriesRepositories moRepository;

    public void save(TimeSeries poTimeSeries)
    {
        moRepository.save(poTimeSeries);
    }
}
