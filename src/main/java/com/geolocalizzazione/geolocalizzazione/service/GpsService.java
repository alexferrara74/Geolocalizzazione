package com.geolocalizzazione.geolocalizzazione.service;

import com.example.model.PercorsoDTO;
import com.example.model.PoiDTO;
import com.geolocalizzazione.geolocalizzazione.constant.ErrorConstant;
import com.geolocalizzazione.geolocalizzazione.exceptions.ApiException;
import com.geolocalizzazione.geolocalizzazione.webClients.GpsApiClient;
import com.geolocalizzazione.geolocalizzazione.webClients.ManutenzioneApi;
import com.manutenzione.model.AutomezzoDTO;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;

@Service
@Log4j2
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
            log.error("Impossibile recuperare la posizione del veicolo {}", numero);
            throw new ApiException(ErrorConstant.BAD_REQUEST);
        }
        return gpsApiClient.recuperaVeicolo(automezzoDTO.getIdSatellitare());
    }


    /**
     *
     * @param idStellitare
     * @param dateStart
     * @param dateEnd
     * @return
     */
    public PercorsoDTO recuperaPercorsoVeicolo(String idStellitare, OffsetDateTime dateStart, OffsetDateTime dateEnd){
        Instant instantDateStart = dateStart.toInstant();
        Instant instantDateEnd = dateEnd.toInstant();
        return gpsApiClient.recuperaPercorsoVeicolo(idStellitare,Timestamp.from(instantDateStart),Timestamp.from(instantDateEnd));
    }

    /**
     *
     * @param targa
     * @param codice
     */
    public void checkIniziale(String targa, Long codice) {

    }

}
