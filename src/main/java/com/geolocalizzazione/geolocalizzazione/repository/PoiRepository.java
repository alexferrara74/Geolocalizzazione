package com.geolocalizzazione.geolocalizzazione.repository;

import com.geolocalizzazione.geolocalizzazione.entity.Poi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PoiRepository extends JpaRepository<Poi, Integer> {


    @Query(" SELECT p FROM Poi p " +
            " JOIN PercorsoPoi pp ON p.id = pp.fkPoi " +
            " WHERE pp.fkPercorso = :fkPercorso " +
            " AND p.dataCancellazione IS NULL AND pp.dataCancellazione IS Null ")
    List<Poi> findByFkPercorsoAndDataCancellazioneIsNull(Integer fkPercorso);

    Poi findByIdAndDataCancellazioneIsNull( Integer id);
}
