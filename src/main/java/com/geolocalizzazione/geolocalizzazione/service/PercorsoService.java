package com.geolocalizzazione.geolocalizzazione.service;


import com.example.model.GenerazionePercorsoDTO;
import com.example.model.PercorsoDTO;
import com.example.model.PercorsoSearchDTO;
import com.example.model.PoiDTO;
import com.geolocalizzazione.geolocalizzazione.constant.ErrorConstant;
import com.geolocalizzazione.geolocalizzazione.entity.*;
import com.geolocalizzazione.geolocalizzazione.exceptions.BadRequestException;
import com.geolocalizzazione.geolocalizzazione.exceptions.ConflictException;
import com.geolocalizzazione.geolocalizzazione.exceptions.NotFoundException;
import com.geolocalizzazione.geolocalizzazione.mapper.PercorsoMapper;
import com.geolocalizzazione.geolocalizzazione.repository.*;
import com.geolocalizzazione.geolocalizzazione.webClients.GeocodingService;
import com.geolocalizzazione.geolocalizzazione.webClients.ManutenzioneApi;
import com.manutenzione.model.AutomezzoDTO;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Log4j2
public class PercorsoService {

    @Autowired
    private GeocodingService geocodingService;
    @Autowired
    private PercorsoMapper percorsoMapper;
    @Autowired
    private PercorsoRepository percorsoRepository;
    @Autowired
    private PercorsoPoiRepository percorsoPoiRepository;
    @Autowired
    private TipoVeicoliRepository tipoVeicoliRepository;
    @Autowired
    private ManutenzioneApi manutenzioneApi;

    public PercorsoDTO createPercorso (List<PoiDTO> poiDTO, Integer idVeicolo, boolean veloce){
        PercorsoDTO response = new PercorsoDTO();
        List<String> coordinateStrings = poiDTO.stream()
                .map(coord -> coord.getLatitudine() + "," + coord.getLongitudine()) // lon,lat
                .toList();
        List<TipoVeicolo> tipiVeicoli = tipoVeicoliRepository.findAll();
        response = geocodingService.calcolaPercorso(coordinateStrings, tipiVeicoli.get(0),veloce);
        return response;
    }

    /**
     * Il metodo permette di salvare a db il percorso ottenuto da valhalla
     * @param percorso
     * @return
     */
    @Transactional
    public GenerazionePercorsoDTO savePercorso (@NonNull GenerazionePercorsoDTO percorso) {
        if ( percorso.getPercorso() == null || percorso.getPercorso().getPois() == null
            || percorso.getPercorso().getPoiPartenza() == null || percorso.getAutomezzo() == null
            || percorso.getAutista() == null) {
             throw new BadRequestException (ErrorConstant.BAD_REQUEST);
        }
        //Vanno effettuate le validazione dei dati passati in input
        final Date date = new Date();
        Percorso percorsoToSave = percorsoMapper.mapPercorsoDtoToPercorso(percorso.getPercorso());
        percorsoToSave.setIdAutista(percorso.getAutista());
        percorsoToSave.setFkVeicolo(percorso.getAutomezzo());
        percorsoToSave.setIdPuntoPartenza(percorso.getPercorso().getPoiPartenza().getId());
        percorsoToSave.setDataCreazione(date);
        percorsoToSave= percorsoRepository.saveAndFlush(percorsoToSave);

        List<PercorsoPoi> percorsoPois = new ArrayList<>();
        //Utility per controllare l'esistenza di tutti i POI
        int ordine = 1;
        for (PoiDTO p : percorso.getPercorso().getPois()){
            PercorsoPoi ps = new PercorsoPoi();
            ps.setFkPercorso(percorsoToSave.getId());
            ps.setFkPoi(p.getId());
            ps.setDataInserimento(date);
            ps.setOrdine(ordine);
            percorsoPois.add(ps);
            ordine = ordine + 1;
        };
        percorsoPoiRepository.saveAllAndFlush(percorsoPois);

        return percorso;
    }

    /**
     *
     * @param filtri
     * @return
     */
    public List<GenerazionePercorsoDTO> getPercorsibyFilter (@NonNull PercorsoSearchDTO filtri){
        List<GenerazionePercorsoDTO> response = new ArrayList<>();
        List<Percorso>percorsi = new ArrayList<>();

        if(filtri.getAutomezzo()!=null) {
            AutomezzoDTO automezzo =  manutenzioneApi.getAutomezzoById(filtri.getAutomezzo());
            if (automezzo == null ){
                throw new NotFoundException(ErrorConstant.BAD_REQUEST);
            }
        }

        if(Boolean.TRUE.equals(filtri.getTerminati())){
            percorsi= percorsoRepository.findByfilter (filtri.getAutista(),filtri.getAutomezzo(),filtri.getData(), Boolean.TRUE);
        } else {
            percorsi =percorsoRepository.findByfilter (filtri.getAutista(),filtri.getAutomezzo(),filtri.getData(), null);
        }

        for (Percorso pr : percorsi){
            GenerazionePercorsoDTO gpd = new GenerazionePercorsoDTO();
            gpd.setPercorso(percorsoMapper.mapPercorsoToPercorsoDto(pr));
            gpd.setAutomezzo(pr.getFkVeicolo());
            gpd.setAutista(pr.getIdAutista());
            response.add(gpd);
        }

        return response;
    }

    /**
     *
     * @param id
     */
    public void archiviaPercorso (Integer id){
        final Date data = new Date();
        Percorso percorso = percorsoRepository.findByIdAndDataCancellazioneIsNullAndDataArchiviazioneIsNull(id);
        if (percorso == null){
            throw new BadRequestException(ErrorConstant.BAD_REQUEST);
        }
        if(Boolean.FALSE.equals(percorso.getTerminato())){
            throw new BadRequestException(ErrorConstant.PERCORSO_NOT_ARCHIVIATO);
        }
        percorso.setDataArchiviazione(data);

        List<PercorsoPoi> pois =percorsoPoiRepository.
                findByFkPercorsoAndDataCancellazioneIsNullAndDataArchiviazioneIsNull(percorso.getId());
        pois.forEach(x->{
            x.setDataArchiviazione(data);
        });
        percorsoRepository.saveAndFlush(percorso);
        percorsoPoiRepository.saveAllAndFlush(pois);
    }

    public void deletePercorso (Integer id){
        final Date data = new Date();
        Percorso percorso = percorsoRepository.findByIdAndDataCancellazioneIsNullAndDataArchiviazioneIsNull(id);
        if (percorso == null){
            throw new BadRequestException(ErrorConstant.BAD_REQUEST);
        }
        percorso.setDataCancellazione(data);

        List<PercorsoPoi> pois =percorsoPoiRepository.
                findByFkPercorsoAndDataCancellazioneIsNullAndDataArchiviazioneIsNull(percorso.getId());
        pois.forEach(x->{
            x.setDataCancellazione(data);
        });
        percorsoRepository.saveAndFlush(percorso);
        percorsoPoiRepository.saveAllAndFlush(pois);
    }

    /**
     * Il metodo permette di aggiornare la consegna per ogni POI di un percorso, controllando se il percoso esiste
     * e non risulti essere stato già terminato.
     * @param idPoi
     * @param idPercorso
     * @param consegnato
     * @param note
     */
    public void aggiornamentoConsegna(@NonNull Integer idPoi,@NonNull Integer idPercorso,@NonNull boolean consegnato, String note){
        Percorso percorso= percorsoRepository.findByIdAndDataCancellazioneIsNullAndDataArchiviazioneIsNull(idPercorso);
        if (percorso == null){
            throw new NotFoundException(ErrorConstant.PERCORSO_NOT_FOUND);
        }
        if(Boolean.TRUE.equals(percorso.getTerminato())){
            throw new ConflictException(ErrorConstant.PERCORSO_TERMINATO);
        }

        PercorsoPoi percorsoPoi = percorsoPoiRepository.findByFkPercorsoAndFkPoiAndDataCancellazioneIsNullAndDataArchiviazioneIsNull(idPercorso,idPoi)
                .orElseThrow(()-> new BadRequestException(ErrorConstant.BAD_REQUEST));

        percorsoPoi.setConsegnato(consegnato);
        percorsoPoi.setDataConsegna(new Date());
        percorsoPoi.setNoteConsegna(StringUtils.hasText(note) ? note : null);

        log.info("Procedo nel salvataggio del Poi con id {} per il percorso con id {}", idPoi, idPercorso);
        percorsoPoiRepository.saveAndFlush(percorsoPoi);

    }

    /**
     *
     * @param idPercorso
     */
    public void terminaPercorso(@NonNull Integer idPercorso){
        Percorso percorso = percorsoRepository.findByIdAndDataCancellazioneIsNullAndDataArchiviazioneIsNull(idPercorso);
        if(percorso == null){
            throw new NotFoundException(ErrorConstant.PERCORSO_NOT_FOUND);
        }
        if(percorsoPoiRepository.checkPoiNotDeliveryForIdPercorso(idPercorso)){
            throw new ConflictException(ErrorConstant.PERCORSO_NOT_CLOSE);
        }

        percorso.setDataTerminazione(new Date());
        percorso.setTerminato(Boolean.TRUE);

        percorsoRepository.saveAndFlush(percorso);

    }

}
