package com.geolocalizzazione.geolocalizzazione.service;

import com.example.api.GpsApi;
import com.example.model.PercorsoDTO;
import com.example.model.PoiDTO;
import com.geolocalizzazione.geolocalizzazione.constant.ErrorConstant;
import com.geolocalizzazione.geolocalizzazione.exceptions.ApiException;
import com.geolocalizzazione.geolocalizzazione.webClients.GpsApiClient;
import com.geolocalizzazione.geolocalizzazione.webClients.ManutenzioneApi;
import com.manutenzione.model.AutomezzoDTO;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;

@Service
public class GpsService {

    @Autowired
    private GpsApiClient gpsApiClient;
    @Autowired
    private ManutenzioneApi manutenzioneApi;

    /**
     *
     * @param numero
     * @return
     */
    public PoiDTO localizzaVeicolo (@NonNull Integer numero){
        AutomezzoDTO automezzoDTO =manutenzioneApi.getAutomezzoById(numero);
        if (automezzoDTO.getIdSatellitare() == null){
            throw new ApiException(ErrorConstant.BAD_REQUEST);
        }
        return gpsApiClient.recuperaVeicolo(automezzoDTO.getIdSatellitare());
    }


    public PercorsoDTO recuperaPercorsoVeicolo(String idStellitare, OffsetDateTime dateStart, OffsetDateTime dateEnd){
        Instant instantDateStart = dateStart.toInstant();
        Instant instantDateEnd = dateEnd.toInstant();
        return gpsApiClient.recuperaPercorsoVeicolo(idStellitare,Timestamp.from(instantDateStart),Timestamp.from(instantDateEnd));
    }

}
