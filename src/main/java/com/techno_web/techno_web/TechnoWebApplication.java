package com.techno_web.techno_web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
public class TechnoWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechnoWebApplication.class, args);
	}

}
