package com.geolocalizzazione.geolocalizzazione.webClients;

import com.example.model.NotificheDTO;
import com.example.model.PercorsoDTO;
import com.example.model.PoiDTO;
import com.example.model.SatellitareNotificaDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geolocalizzazione.geolocalizzazione.mapper.NotificheMapper;
import com.geolocalizzazione.geolocalizzazione.polling.SatellitarePollingJob;
import com.geolocalizzazione.geolocalizzazione.utils.ManutenzioneApiUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.util.*;

@Log4j2
@Service
public class GpsApiClient {

    @Value("${paj-gps.email}")
    private String email;
    @Value("${paj-gps.password}")
    private String password;
    @Value("${paj-gps.url}")
    private String url;


    @Autowired
    private WebClient webClient;
    @Autowired
    private NotificheMapper notificheMapper;
    @Autowired
    private ManutenzioneApiUtils manutenzioneApiUtils;


    public String loginPAJ() {
       try {
           String path = "/api/v1/login";
           String urlLogin = UriComponentsBuilder.fromHttpUrl(url)
                   .path(path)
                   .queryParam("email", email)
                   .queryParam("password", password)
                   .toUriString();

           Map<String, Object> response = webClient
                   .post()
                   .uri(urlLogin)
                   .accept(MediaType.APPLICATION_JSON)
                   .retrieve()
                   .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                   .block();
           if (response != null && response.containsKey("success")) {
               Map<String, Object> success = (Map<String, Object>) response.get("success");
               String token = (String) success.get("token");
               return token;
           } else {
               log.warn("Risposta del login senza campo 'success': " + response);
           }
       }catch(Exception e){
           log.error("Errore nel login del satellitare: ",e);
       }
    return null;
    }

    public PoiDTO recuperaVeicolo(String idSatellitare) {
        String token = loginPAJ();
        PoiDTO dto = new PoiDTO();

        try {
            WebClient webClient = WebClient.create(url);
            String path = "/api/v1/trackerdata/getalllastpositions";

            Map<String, Object> body = new HashMap<>();
            body.put("deviceIDs", List.of(idSatellitare));
            body.put("fromLastPoint", false);

            Map<String, Object> responseMap = webClient.post()
                    .uri(path)
                    .header("Authorization", "Bearer " + token)
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                    .block(); // 🔴 attende il risultato

            if (responseMap != null && responseMap.get("success") instanceof List<?> successList && !successList.isEmpty()) {
                Object firstElement = successList.get(0);
                if (firstElement instanceof Map<?, ?> map) {
                    double lat = ((Number) map.get("lat")).doubleValue();
                    double lng = ((Number) map.get("lng")).doubleValue();
                    dto.setLatitudine(lat);
                    dto.setLongitudine(lng);
                }
            }

        } catch (Exception e) {
            log.error("Errore nel recupero posizione: ", e);
        }

        return dto;
    }

    public PercorsoDTO recuperaPercorsoVeicolo(String idSatellitare, Timestamp dateStart, Timestamp dateEnd) {
        String token = loginPAJ();
        PercorsoDTO percorsoDTO = new PercorsoDTO();

        try {
            WebClient webClient = WebClient.create(url);
            String path = "/api/v1/trackerdata/"+idSatellitare+"/date_range";

            String urlPerocorso = UriComponentsBuilder.fromHttpUrl(url)
                    .path(path)
                    .queryParam("dateStart", dateStart.getTime() / 1000)
                    .queryParam("dateEnd", dateEnd.getTime() / 1000)
                    .toUriString();

            Map<String, Object> responseMap = webClient.get()
                    .uri(urlPerocorso)
                    .header("Authorization", "Bearer " + token)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                    .block(); // 🔴 attende il risultato


            // Gestire la risposta
            if (responseMap != null && responseMap.containsKey("success")) {
                List<Map<String, Object>> successList = (List<Map<String, Object>>) responseMap.get("success");

                successList.sort(Comparator.comparingLong(item -> {
                    Object dateunixObj = item.get("dateunix");
                    if (dateunixObj instanceof Number) {
                        return ((Number) dateunixObj).longValue();  // Convertiamo qualsiasi numero (Integer, Long, ecc.) in Long
                    }
                    throw new ClassCastException("La chiave 'dateunix' non è un tipo numerico");
                }));

                // Creazione di una lista di PoiDTO (latitudine e longitudine)
                List<PoiDTO> pois = new ArrayList<>();
                for (Map<String, Object> entry : successList) {
                    PoiDTO poi = new PoiDTO();
                    poi.setLatitudine((Double) entry.get("lat"));
                    poi.setLongitudine((Double) entry.get("lng"));
                    pois.add(poi);
                }

                // Gestire poiPartenza e pois
                if (!pois.isEmpty()) {
                    percorsoDTO.setPoiPartenza(pois.get(0)); // Primo punto di partenza
                    percorsoDTO.setPois(pois.subList(1, pois.size())); // Resto dei pois
                }

                percorsoDTO.setDistance(((Number) responseMap.get("distance")).doubleValue());

            }
                return percorsoDTO;



        } catch (Exception e) {
            log.error("Errore nel recupero posizione: ", e);
        }

        return percorsoDTO;
    }

    public List<NotificheDTO> getNotificheVeicolo () {
        ObjectMapper mapper = new ObjectMapper();

        String token = loginPAJ();
        WebClient webClient = WebClient.create(url);
        String path = "/api/v1/notifications";

        String urlNotifiche = UriComponentsBuilder.fromHttpUrl(url)
                .path(path)
                .toUriString();
        List<SatellitareNotificaDTO> responseList = webClient.get()
                .uri(urlNotifiche)
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .map(json -> notificheMapper.mapFromJson(json))
                .block(); // <- blocca fino al completamento


        log.info("Trovate {} notifiche", responseList.size());

       return manutenzioneApiUtils.recuperaVeicoloAssociato(responseList);
    }

    public void deleteNotificheVeicoli (String idDevice) {
        ObjectMapper mapper = new ObjectMapper();
    try {
        String token = loginPAJ();
        WebClient webClient = WebClient.create(url);
        String path = "/api/v1/notifications/markReadByDevice/"+ idDevice;

        String urlNotifiche = UriComponentsBuilder.fromHttpUrl(url)
                .path(path)
                .queryParam("isRead", "1")
                .toUriString();
        webClient.put()
                .uri(urlNotifiche)
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block(); // <- blocca fino al completamento

    }catch (Exception e){
        log.error("Errore durante la lettura delle notifiche per il dispositivo con ID {}", idDevice);
    }
    }



}