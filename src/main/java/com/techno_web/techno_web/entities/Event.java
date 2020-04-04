package com.techno_web.techno_web.entities;

import java.util.Calendar;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.persistence.GeneratedValue;





@Entity
@Table
public class Event {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private UUID id ;
	
	@NotNull
	@Column
	private Calendar event_date;
	
	@NotNull
	@Column
	private Float value;
	
	
	@Column
	private String tag;
	
	@Column
	private String comments;

	public UUID  getId() {
		return id;
	}

	public void setId(UUID  id) {
		this.id = id;
	}

	public Calendar getEvent_date() {
		return event_date;
	}

	public void setEvent_date(Calendar event_date) {
		this.event_date = event_date;
	}

	public Float getValue() {
		return value;
	}

	public void setValue(Float value) {
		this.value = value;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
	

}
