package com.techno_web.techno_web.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Calendar;
import java.util.List;

public class EventDto {
	
	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private Calendar time;
	
	private Float value;
	
	private List<String> tags;
	
	private String Comment;

	public Calendar getTime() {
		return time;
	}

	public void setTime(Calendar time) {
		this.time = time;
	}

	public Float getValue() {
		return value;
	}

	public void setValue(Float value) {
		this.value = value;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getComment() {
		return Comment;
	}

	public void setComment(String comment) {
		Comment = comment;
	}
	

}
