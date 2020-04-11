package com.techno_web.techno_web.dto;

import java.util.List;

public class TimeSeriesDetailDto {
	
	private String id;
	
	private String title;
	
	private String description;
	
	private boolean hasModificationRight;

	private List<EventDto> eventList;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isHasModificationRight() {
		return hasModificationRight;
	}

	public void setHasModificationRight(boolean hasModificationRight) {
		this.hasModificationRight = hasModificationRight;
	}

	public List<EventDto> getEventList() {
		return eventList;
	}

	public void setEventList(List<EventDto> eventList) {
		this.eventList = eventList;
	}
	
}
