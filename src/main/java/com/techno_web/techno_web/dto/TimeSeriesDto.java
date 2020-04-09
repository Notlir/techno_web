package com.techno_web.techno_web.dto;

public class TimeSeriesDto {
	
	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private String title;
	
	private String description;
	
	private boolean hasModificationRight;
	
	public TimeSeriesDto(String title, String description, boolean hasModification)
	{
		this.title=title;
		this.description = description;
		this.hasModificationRight = hasModification;
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

}
