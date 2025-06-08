package com.geolocalizzazione.geolocalizzazione.mapper;


import com.example.model.PercorsoDTO;
import com.geolocalizzazione.geolocalizzazione.entity.Percorso;
import com.geolocalizzazione.geolocalizzazione.entity.Poi;
import com.geolocalizzazione.geolocalizzazione.repository.PercorsoPoiRepository;
import com.geolocalizzazione.geolocalizzazione.repository.PoiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.List;

@Component
public class PercorsoMapper {

    @Autowired
    private PercorsoPoiRepository percorsoPoiRepository;
    @Autowired
    private PoiRepository poiRepository;
    @Autowired
    private PoiMapper poiMapper;

   public Percorso mapPercorsoDtoToPercorso (PercorsoDTO percorso) {
       if ( percorso == null ) {
           return null;
       }
       Percorso response = new Percorso();
        response.setDistanzaPercorsa(percorso.getDistance());
        response.setTempoImpiegato(percorso.getDuration());
        response.setShape(percorso.getShape());

       return response;
   }

   public PercorsoDTO mapPercorsoToPercorsoDto (Percorso percorso){
       if ( percorso == null ) {
           return null;
       }
       PercorsoDTO response = new PercorsoDTO();
       response.setId(percorso.getId());
       response.setDuration(percorso.getTempoImpiegato()!=null ? (double)percorso.getTempoImpiegato(): null);
       response.setDistance(percorso.getDistanzaPercorsa());
       response.setShape(percorso.getShape());
       List<Poi> pois= poiRepository.findByFkPercorsoAndDataCancellazioneIsNull(percorso.getId());
       response.setPois(pois.stream().map(x->poiMapper.mapPoiToPoiDto(x)).toList());
       Poi poi = poiRepository.findByIdAndDataCancellazioneIsNull(percorso.getIdPuntoPartenza());
       response.setPoiPartenza(poiMapper.mapPoiToPoiDto(poi));
       response.setDataCreazione(percorso.getDataCreazione().toInstant()
               .atZone(ZoneId.systemDefault())
               .toLocalDate());
       return response;
   }
}
