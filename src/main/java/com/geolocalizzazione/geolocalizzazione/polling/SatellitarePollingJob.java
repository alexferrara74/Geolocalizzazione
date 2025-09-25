package com.geolocalizzazione.geolocalizzazione.polling;

import com.example.model.NotificheDTO;
import com.example.model.SatellitareNotificaDTO;
import com.geolocalizzazione.geolocalizzazione.webClients.GpsApiClient;
import com.geolocalizzazione.geolocalizzazione.webSocket.WebSocketNotifiche;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class SatellitarePollingJob {

    @Autowired
    private GpsApiClient gpsApiClient;
    @Autowired
    private WebSocketNotifiche webSocketNotifiche;

    @Scheduled(fixedDelay = 10000)
    @Async("asyncExecutor")
    public void poll() {
      List<NotificheDTO> notifiche = gpsApiClient.getNotificheVeicolo();
      log.info("Eseguita ricerca notifiche per i veicoli, trovate {} notifiche ", notifiche.size());
      webSocketNotifiche.broadcast(notifiche);
    }
}
