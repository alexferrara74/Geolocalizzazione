package com.geolocalizzazione.geolocalizzazione.utils;

import com.example.model.NotificheDTO;
import com.example.model.SatellitareNotificaDTO;
import com.geolocalizzazione.geolocalizzazione.webClients.ManutenzioneApi;
import com.manutenzione.model.AutomezzoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ManutenzioneApiUtils {

    @Autowired
    private ManutenzioneApi manutenzioneApi;

    public List<NotificheDTO> recuperaVeicoloAssociato (List<SatellitareNotificaDTO> listSatellitareNotificaDto) {
        List<NotificheDTO> response = new ArrayList<>();
        Set<Integer> idsSatellitare = new HashSet<>();
        HashMap<Integer, List<SatellitareNotificaDTO>> alertForIdSatellitare = new HashMap<>();

        if (listSatellitareNotificaDto!=null && !listSatellitareNotificaDto.isEmpty()) {
            listSatellitareNotificaDto.forEach(alert->{
                //Controllo che nella mappa non ci sia già l'id satellitare, se esiste aggiungo l'alert alla lista
                if(alertForIdSatellitare.get(alert.getIddevice()) == null){
                    alertForIdSatellitare.put(alert.getIddevice(), new ArrayList<>(List.of(alert)));
                } else {
                    alertForIdSatellitare.get(alert.getIddevice()).add(alert);
                }
                idsSatellitare.add(alert.getIddevice());
            });
            List<AutomezzoDTO> automezzi =
                    manutenzioneApi.getAutomezzoByIdsSatellitare(idsSatellitare.stream().map(Object::toString).toList());

            //Per ogni automezzo costruisco la response
            automezzi.forEach(aut->{
                NotificheDTO notifica = new NotificheDTO();
                notifica.setNumeroAutomezzo(aut.getNumero().longValue());
                notifica.setTargaAutomezzo(aut.getTarga());
                notifica.setAlert(alertForIdSatellitare.get(Integer.parseInt(aut.getIdSatellitare())));
                response.add(notifica);
            });

        }


        return response;
    }


}
