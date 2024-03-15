package com.example.soccer_club_backend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class SoccerClubBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(SoccerClubBackendApplication.class, args);

		log.info("App Was started!");
	}
}
