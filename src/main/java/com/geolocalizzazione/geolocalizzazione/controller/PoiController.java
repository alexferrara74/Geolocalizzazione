package com.geolocalizzazione.geolocalizzazione.controller;

import com.example.api.PoiApi;
import com.example.model.PoiDTO;
import com.geolocalizzazione.geolocalizzazione.service.PoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PoiController implements PoiApi {

    @Autowired
    private PoiService poiService;

    @Override
    public ResponseEntity<PoiDTO> createPoi(PoiDTO poiDTO) {
        PoiDTO response = poiService.creaPoi(poiDTO);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<PoiDTO>> getAllPoi() {
        List<PoiDTO> response = poiService.getAllPoi();
        return ResponseEntity.ok(response);
    }
}
