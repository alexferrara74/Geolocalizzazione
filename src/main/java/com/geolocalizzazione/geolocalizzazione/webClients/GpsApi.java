package com.geolocalizzazione.geolocalizzazione.webClients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
public class GpsApi {


    @Autowired
    private WebClient webClient;

    public void ExternalApiService(WebClient.Builder webClientBuilder) {
        webClient = webClientBuilder.baseUrl("https://connect.paj-gps.de/docs/api-docs.json").build();
    }

    public String loginPAJ() {
        return webClient
                .post()
                .uri(uriBuilder -> uriBuilder.path("/api/v1/login")
                        .queryParam("email", "alexferrara74@gmail.com")
                        .queryParam("password", "")
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block(); // blocca per ottenere il dato in modo sincrono
    }
}
