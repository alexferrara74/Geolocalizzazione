package com.geolocalizzazione.geolocalizzazione.controller;

import com.example.api.GpsApi;
import com.example.model.PercorsoDTO;
import com.example.model.PoiDTO;
import com.geolocalizzazione.geolocalizzazione.service.GpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@RestController
public class GpsController implements GpsApi {

    @Autowired
    private GpsService gpsService;

    @Override
    public ResponseEntity<PoiDTO> getPosizioneGpsVeicolo(Integer numero) {
        PoiDTO response = gpsService.localizzaVeicolo(numero);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<PercorsoDTO> getPercorsoByDate(String idDispositivo, OffsetDateTime dateStart, OffsetDateTime dateEnd) {
       PercorsoDTO response = gpsService.recuperaPercorsoVeicolo(idDispositivo,dateStart,dateEnd);
       return ResponseEntity.ok(response);
    }
}
