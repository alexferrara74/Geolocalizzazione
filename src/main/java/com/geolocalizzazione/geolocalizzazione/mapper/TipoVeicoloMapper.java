package com.geolocalizzazione.geolocalizzazione.mapper;

import com.example.model.TipoVeicoliDTO;
import com.geolocalizzazione.geolocalizzazione.entity.TipoVeicolo;
import org.springframework.stereotype.Component;

@Component
public class TipoVeicoloMapper {

    public TipoVeicoliDTO mapTipoToTipoDto (TipoVeicolo tipoVeicolo){
        if(tipoVeicolo == null){
            return null;
        }
        TipoVeicoliDTO response = new TipoVeicoliDTO();
        response.setId(tipoVeicolo.getId());
        response.setKey(tipoVeicolo.getKey());
        response.setValue(tipoVeicolo.getValue());
        return response;
    }




}
