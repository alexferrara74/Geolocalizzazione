package com.geolocalizzazione.geolocalizzazione.controller;

import com.example.api.NotificheApi;
import com.geolocalizzazione.geolocalizzazione.polling.SatellitarePollingJob;
import com.geolocalizzazione.geolocalizzazione.webClients.GpsApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificheController implements NotificheApi {

    @Autowired
    private GpsApiClient gpsApiClient;
    @Autowired
    private SatellitarePollingJob satellitarePollingJob;


    @Override
    public ResponseEntity<Void> deleteAlert(String idDispositivo) {
        gpsApiClient.deleteNotificheVeicoli(idDispositivo);
        satellitarePollingJob.poll();
        return ResponseEntity.ok().build();
    }
}
