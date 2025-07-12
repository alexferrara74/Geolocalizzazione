package com.geolocalizzazione.geolocalizzazione.controller;

import com.geolocalizzazione.geolocalizzazione.service.PercorsoService;
import com.example.api.PercorsoApi;
import com.example.model.GenerazionePercorsoDTO;
import com.example.model.PercorsoDTO;
import com.example.model.PercorsoSearchDTO;
import com.example.model.PoiDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PercorsoController implements PercorsoApi {

    @Autowired
    private PercorsoService percorsoService;

    @Override
    public ResponseEntity<PercorsoDTO> createPercorso(Integer idVeicolo, Boolean percorsoBreve, List<PoiDTO> poiDTO) {
        PercorsoDTO response = percorsoService.createPercorso(poiDTO,idVeicolo,percorsoBreve);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<GenerazionePercorsoDTO> savePercorso(GenerazionePercorsoDTO generazionePercorsoDTO) {
        GenerazionePercorsoDTO response = percorsoService.savePercorso(generazionePercorsoDTO);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<GenerazionePercorsoDTO>> getPercorsiByFilter(PercorsoSearchDTO percorsoSearchDTO) {
        List<GenerazionePercorsoDTO> response = percorsoService.getPercorsibyFilter(percorsoSearchDTO);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> percorsoIdArchiviaPatch(Integer id) {
        percorsoService.archiviaPercorso(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> percorsoIdDelete(Integer id) {
        percorsoService.deletePercorso(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> aggiornamentoConsegna(Integer idPoi, Integer idPercorso, Boolean consegnato, String note) {
        percorsoService.aggiornamentoConsegna(idPoi,idPercorso,consegnato,note);
        return ResponseEntity.ok().build();    }
}


