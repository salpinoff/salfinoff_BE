package com.server.salpinoffServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SalpinoffServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalpinoffServerApplication.class, args);
	}

}
