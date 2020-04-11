package com.techno_web.techno_web.entities;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table
public class UserRight implements Serializable {
	

	private static final long serialVersionUID = -1908601800042623845L;

	@Id
	private UUID id;
	
	@ManyToOne
	private TimeSeries timeseries;
	
	
	@Column(name="right")
	private Boolean hasModificationRight;
	
	
	public UserRight(TimeSeries poSeries, Boolean pbRight)
	{
		this.timeseries=poSeries;
		this.hasModificationRight=pbRight;
	}
	

	public UUID getId() {
		return id;
	}


	public void setId(UUID id) {
		this.id = id;
	}


	public TimeSeries getTimeseries() {
		return timeseries;
	}


	public void setTimeseries(TimeSeries timeseries) {
		this.timeseries = timeseries;
	}


	public Boolean getHasModificationRight() {
		return hasModificationRight;
	}

	public void setHasModificationRight(Boolean hasModificationRight) {
		this.hasModificationRight = hasModificationRight;
	}

}
