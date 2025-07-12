package com.geolocalizzazione.geolocalizzazione.webClients;


import com.example.model.PercorsoDTO;
import com.geolocalizzazione.geolocalizzazione.constant.ErrorConstant;
import com.geolocalizzazione.geolocalizzazione.constant.GeocodingConstant;
import com.geolocalizzazione.geolocalizzazione.entity.TipoVeicolo;
import com.geolocalizzazione.geolocalizzazione.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class GeocodingService {
    @Value("${valhalla.url}")
    private String url;

    @Autowired
    private WebClient webClient;

        public PercorsoDTO calcolaPercorso(List<String> points, TipoVeicolo tipoVeicolo, boolean percorso) {

            try {
                webClient = WebClient.create(url);
                String veicolo="";
                if( tipoVeicolo.getKey().equalsIgnoreCase(GeocodingConstant.AUTO) &&  Boolean.FALSE.equals(percorso)){
                    veicolo = GeocodingConstant.AUTO_SHORTER;
                }else{
                    veicolo= tipoVeicolo.getKey();
                }

                List<Map<String, Double>> locations = points.stream()
                        .map(p -> {
                            String[] split = p.split(",");
                            double lat = Double.parseDouble(split[0]);  // latitudine
                            double lon = Double.parseDouble(split[1]); // longitudine
                            Map<String, Double> pointMap = new HashMap<>();
                            pointMap.put("lat", lat); // aggiungi latitudine
                            pointMap.put("lon", lon); // aggiungi longitudine
                            return pointMap;
                        })
                        .toList();

                Map<String, Object> dateTime = new HashMap<>();
                dateTime.put("type",1);
                // Formatta la data in ISO-8601 (es. "2025-07-04T09:00")
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                String formattedDate = LocalDateTime.now().format(formatter);
                dateTime.put("value", formattedDate);

                Map<String, Map<String,Boolean>> costingOption = new HashMap<>();
                Map<String,Boolean> traffic= new HashMap<>();
                traffic.put("use_traffic",true);
                costingOption.put(veicolo,traffic);

                Map<String, Object> requestBody = Map.of(
                        "locations", locations,
                        "costing", veicolo,
                        "directions_options", Map.of("units", "kilometers", "language", "it-IT"),
                        "avoid_features", List.of("highways"),
                        "source", "first",
                        "date_time", dateTime,
                        "costing_options",costingOption
                );


                Map<String, Object> osrmResponse = webClient
                        .post()
                        .uri("/optimized_route")
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(requestBody)
                        .retrieve()
                        .onStatus(
                                HttpStatusCode::is4xxClientError,
                                clientResponse -> clientResponse.bodyToMono(String.class)
                                        .map(b -> new RuntimeException("Client error: " + b))
                        )
                        .onStatus(
                                HttpStatusCode::is5xxServerError,
                                clientResponse -> clientResponse.bodyToMono(String.class)
                                        .map(b-> new RuntimeException("Server error: " + b))
                        )
                        .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                        })
                        .block();
                Map<String, Object> trip = (Map<String, Object>) osrmResponse.get("trip");
                Map<String, Object> summary = (Map<String, Object>) trip.get("summary");
                List<Map<String, Object>> legsList = (List<Map<String, Object>>) trip.get("legs");
                List<String> shape = new ArrayList<>();
                legsList.forEach(x->{
                   shape.add(x.get("shape").toString());
                });

                PercorsoDTO response = new PercorsoDTO();
                response.setDistance((double)summary.get("length"));
                response.setDuration((double)summary.get("time"));
                response.setShape(shape);
                return response;
            }catch (Exception e){
                throw new BadRequestException(ErrorConstant.BAD_REQUEST);
            }
        }

}


