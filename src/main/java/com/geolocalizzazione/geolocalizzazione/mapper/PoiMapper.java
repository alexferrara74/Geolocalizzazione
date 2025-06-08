package com.geolocalizzazione.geolocalizzazione.mapper;


import com.example.model.PoiDTO;
import com.geolocalizzazione.geolocalizzazione.entity.Poi;
import com.geolocalizzazione.geolocalizzazione.repository.PoiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PoiMapper {

    @Autowired
    private PoiRepository poiRepository;

   public PoiDTO mapPoiToPoiDto (Poi poi){
       if (poi == null) {
        return null;
       }
       PoiDTO response = new PoiDTO();
       response.setId(poi.getId());
       response.setDescrizione(poi.getDescrizione());
       response.setIndirizzo(poi.getIndirizzo());
       response.setLatitudine(poi.getLatitudine());
       response.setLongitudine(poi.getLongitudine());
       response.setNome(poi.getNome());

       return response;
   }

    public Poi mapPoiDtoToPoi (PoiDTO poi){
        if (poi == null) {
            return null;
        }
        Poi response = new Poi();
        response.setDescrizione(poi.getDescrizione());
        response.setIndirizzo(poi.getIndirizzo());
        response.setNome(poi.getNome());
        response.setLatitudine(poi.getLatitudine());
        response.setLongitudine(poi.getLongitudine());
        return response;
    }



}
