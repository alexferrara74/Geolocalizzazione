package com.geolocalizzazione.geolocalizzazione.mapper;

import com.example.model.SatellitareNotificaDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Component
public class NotificheMapper {

        private final ObjectMapper mapper = new ObjectMapper();



        public List<SatellitareNotificaDTO> mapFromJson(String json) {
            JsonNode root;
            try {
                root = mapper.readTree(json);
            }catch (Exception e){
                log.error("Errore nella lettura del json Notifiche");
                return null;
            }
            List<SatellitareNotificaDTO> list = new ArrayList<>();

            // recupera "success" dal JSON
            JsonNode successNode = root.get("success");
            if (successNode != null && !successNode.isNull()) {
                successNode.forEach(node -> {
                    SatellitareNotificaDTO dto = new SatellitareNotificaDTO();

                    dto.setId(node.path("id").asInt());
                    dto.setIddevice(node.path("iddevice").asInt());
                    // dto.setName(node.path("name").asText(null));
                    dto.setIcon(node.path("icon").asText(null));
                    dto.setBezeichnung(node.path("bezeichnung").asText(null));
                    // dto.setMeldung(node.path("meldung").asText(null));
                    dto.setMeldungtyp(node.path("meldungtyp").asInt());
                    //dto.setDateunix(node.path("dateunix").asLong());
                    dto.setLat(node.path("lat").asDouble());
                    dto.setLng(node.path("lng").asDouble());
                    dto.setIsread(node.path("isread").asInt());
                    // dto.setImei(node.path("imei").asText(null));
                    // dto.setSpeed(node.path("speed").asDouble());
                    // dto.setSpeederlaubt(node.path("speederlaubt").asDouble());
                    dto.setRadiusin(node.path("radiusin").asInt());
                    dto.setRadiusout(node.path("radiusout").asInt());
                    dto.setZuendon(node.path("zuendon").asInt());
                    dto.setZuendoff(node.path("zuendoff").asInt());
                    dto.setPush(node.path("push").asInt());

                    list.add(dto);

                });

            }
            return list;
        }
    }
