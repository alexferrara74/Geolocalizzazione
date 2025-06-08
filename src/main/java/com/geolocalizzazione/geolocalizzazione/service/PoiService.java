package com.geolocalizzazione.geolocalizzazione.service;

import com.example.model.PoiDTO;
import com.geolocalizzazione.geolocalizzazione.constant.ErrorConstant;
import com.geolocalizzazione.geolocalizzazione.entity.Poi;
import com.geolocalizzazione.geolocalizzazione.exceptions.BadRequestException;
import com.geolocalizzazione.geolocalizzazione.mapper.PoiMapper;
import com.geolocalizzazione.geolocalizzazione.repository.PoiRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PoiService {

    @Autowired
    private PoiRepository poiRepository;
    @Autowired
    private PoiMapper poiMapper;

    @Transactional
    public PoiDTO creaPoi(PoiDTO dto) {
       if(dto == null || dto.getNome() == null || dto.getDescrizione() == null
               || dto.getLatitudine() == null || dto.getLongitudine() == null){
           throw new BadRequestException(ErrorConstant.BAD_REQUEST);
       }
        Poi poi = poiMapper.mapPoiDtoToPoi(dto);
        poi.setDataCreazione(new Date());
        return poiMapper.mapPoiToPoiDto(poiRepository.saveAndFlush(poi));
    }

    public List<PoiDTO> getAllPoi (){
        List<Poi> pois = poiRepository.findAll();
        return pois.stream().map(x-> poiMapper.mapPoiToPoiDto(x)).toList();
    }
}
