package com.geolocalizzazione.geolocalizzazione.repository;
import com.geolocalizzazione.geolocalizzazione.entity.PercorsoPoi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PercorsoPoiRepository extends JpaRepository<PercorsoPoi, Integer> {

   List<PercorsoPoi> findByFkPercorsoAndDataCancellazioneIsNullAndDataArchiviazioneIsNull (Integer fkPercorso);
}
