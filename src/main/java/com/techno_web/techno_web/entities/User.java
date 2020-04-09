package com.techno_web.techno_web.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.persistence.GeneratedValue;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Entity
@Table
public class User {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id ;
	
	@NotNull
	@Column
	private String login;
	
	@NotNull
	@Column
	private String salt;
	
	@NotNull
	@Column
	private String password;
	
	@Column
	private String token;
	
	@Column
	private Calendar token_creation;
	
	@OneToMany
	private List<TimeSeries> series_with_write_rights;
	
	@OneToMany
	private List<TimeSeries> series_with_read_rights;
	


	public List<TimeSeries> getSeries_with_write_rights() {
		return series_with_write_rights;
	}

	public void setSeries_with_write_rights(List<TimeSeries> series_with_write_rights) {
		this.series_with_write_rights = series_with_write_rights;
	}

	public List<TimeSeries> getSeries_with_read_rights() {
		return series_with_read_rights;
	}

	public void setSeries_with_read_rights(List<TimeSeries> series_with_read_rights) {
		this.series_with_read_rights = series_with_read_rights;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String etag) {
		this.token = etag;
	}

	public Calendar getToken_creation() {
		return token_creation;
	}

	public void setToken_creation(Calendar etag_creation) {
		this.token_creation = etag_creation;
	}


	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
}
