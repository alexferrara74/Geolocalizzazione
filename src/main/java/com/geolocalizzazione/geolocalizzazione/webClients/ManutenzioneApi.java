package com.geolocalizzazione.geolocalizzazione.webClients;

import com.geolocalizzazione.geolocalizzazione.utils.JwtUtils;
import com.manutenzione.model.AutistaDTO;
import com.manutenzione.model.AutomezzoDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;


@Service
@Log4j2
public class ManutenzioneApi {

    @Value("${ManutenzioneApi.automezzoById}")
    private String automezzoById;
    @Value("${ManutenzioneApi.autistaById}")
    private String autistaById;
    @Value("${ManutenzioneApi.automezzoByIdsSatellitare}")
    private String automezzoByIdsSatellitare;



    @Autowired
    private WebClient webClient;
    @Autowired
    private JwtUtils jwtUtils;

    public AutomezzoDTO getAutomezzoById(Integer id) {
        try {
            String jwt = getJwtFromRequest();
            if (jwt == null) {
                jwt = jwtUtils.generateToken("admin");
            }
            String url = automezzoById;  // concatena l'id
            return webClient.get()
                    .uri(url + "{numero}", id)
                    .header("Authorization", "Bearer " + jwt)
                    .retrieve()
                    .bodyToMono(AutomezzoDTO.class)
                    .block();
        } catch (Exception ex) {
            log.error("Impossibile recuperare l'automezzo con id {}", id);
            return null;
        }
    }

    public AutistaDTO getAutistaById(Integer id) {
        try {
            String jwt = getJwtFromRequest();
            String url = autistaById + "/" + id;  // concatena l'id

            return webClient.get()
                    .uri(url, id)
                    .header("Authorization", "Bearer " + jwt)
                    .retrieve()
                    .bodyToMono(AutistaDTO.class)
                    .block();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<AutomezzoDTO> getAutomezzoByIdsSatellitare(List<String> idsSatellitare) {
        try {
            WebClient webClient = WebClient.builder()
                    .baseUrl("http://127.0.0.1:8080") // <-- host e porta corretti
                    .build();

            String jwt = getJwtFromRequest();

            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/Automezzo/satellitare")
                            .queryParam("idsSatellitare", idsSatellitare)
                            .build())
                    .header("Authorization", "Bearer " + jwt)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<AutomezzoDTO>>() {})
                    .block();

        } catch (Exception ex) {
            log.error("errore nel recupero dei veicoli :{}", ex);
            return new ArrayList<>();
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
