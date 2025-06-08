package com.geolocalizzazione.geolocalizzazione.webClients;

import com.manutenzione.model.AutomezzoDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.client.WebClient;


@Service
public class ManutenzioneApi {

    @Autowired
    private WebClient webClient;

    public AutomezzoDTO getAutomezzoById(Integer id) {
        try {
            String jwt = getJwtFromRequest();

            return webClient.get()
                    .uri("http://127.0.0.1:8080/Automezzo/{id}", id)
                    .header("Authorization", "Bearer " + jwt)
                    .retrieve()
                    .bodyToMono(AutomezzoDTO.class)
                    .block();
        } catch (Exception ex) {
            // log exception
            return null;
        }
    }

    private String getJwtFromRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) return null;
        HttpServletRequest request = attributes.getRequest();
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7); // rimuove "Bearer "
        }
        return null;
    }
}
