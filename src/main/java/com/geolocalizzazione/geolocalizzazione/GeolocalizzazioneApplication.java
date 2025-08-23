package com.geolocalizzazione.geolocalizzazione;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GeolocalizzazioneApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeolocalizzazioneApplication.class, args);
	}

}
