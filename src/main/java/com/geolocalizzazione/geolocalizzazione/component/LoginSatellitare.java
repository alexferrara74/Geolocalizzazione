package com.geolocalizzazione.geolocalizzazione.component;
import com.geolocalizzazione.geolocalizzazione.webClients.GpsApiClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

    @Component
    @Log4j2
    public class LoginSatellitare implements CommandLineRunner {

        @Autowired
        private GpsApiClient gpsApiClient;

        @Override
        public void run(String... args) {
            log.info("Applicazione avviata! Eseguo setup iniziale...");
            gpsApiClient.loginPAJ();
            log.info("Login Satellitare avvenuto con successo....");
        }
    }


