package com.geolocalizzazione.geolocalizzazione.repository;
import com.geolocalizzazione.geolocalizzazione.entity.PercorsoPoi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.List;

public interface PercorsoPoiRepository extends JpaRepository<PercorsoPoi, Integer> {

   List<PercorsoPoi> findByFkPercorsoAndDataCancellazioneIsNullAndDataArchiviazioneIsNull (Integer fkPercorso);

   Optional<PercorsoPoi> findByFkPercorsoAndFkPoiAndDataCancellazioneIsNullAndDataArchiviazioneIsNull(Integer fkPercorso, Integer fkPoi);


   @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM PercorsoPoi p " +
           "WHERE p.fkPercorso = :idPercorso " +
           "AND p.dataCancellazione IS NULL AND p.dataArchiviazione IS NULL AND p.consegnato IS NULL ")
   Boolean checkPoiNotDeliveryForIdPercorso(Integer idPercorso);
}
