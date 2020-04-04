package com.techno_web.techno_web.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.persistence.GeneratedValue;
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
