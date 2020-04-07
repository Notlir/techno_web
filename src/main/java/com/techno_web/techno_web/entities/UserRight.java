package com.techno_web.techno_web.entities;

import java.io.Serializable;

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
	@ManyToOne
	private User user;
	
	
	@Column(name="right")
	private Boolean hasModificationRight;
	
	
	public UserRight(TimeSeries poSeries, User poUser, Boolean pbRight)
	{
		this.user=poUser;
		this.hasModificationRight=pbRight;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Boolean getHasModificationRight() {
		return hasModificationRight;
	}

	public void setHasModificationRight(Boolean hasModificationRight) {
		this.hasModificationRight = hasModificationRight;
	}

}
