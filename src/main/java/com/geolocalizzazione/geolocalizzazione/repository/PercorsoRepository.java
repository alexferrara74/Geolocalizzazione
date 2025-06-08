package com.geolocalizzazione.geolocalizzazione.repository;
import com.geolocalizzazione.geolocalizzazione.entity.Percorso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface PercorsoRepository extends JpaRepository<Percorso, Integer> {
    List<Percorso> findByDataCancellazioneIsNull ();

    @Query("SELECT p FROM Percorso p " +
            " JOIN Automezzo a ON a.numero = p.fkVeicolo " +
            " JOIN Autista at ON at.id = p.idAutista " +
            " WHERE p.dataCancellazione IS NULL " +
            " AND (:idAutista IS NULL OR p.idAutista = :idAutista) " +
            " AND  (:idAutomezzo IS NULL OR p.fkVeicolo = :idAutomezzo) " +
            " AND (:data IS NULL OR FUNCTION('DATE', p.dataCreazione) = :data) " +
            " ORDER BY p.dataCreazione desc ")
    List<Percorso> findByfilter(Integer idAutista, Integer idAutomezzo, LocalDate data);

    Percorso findByIdAndDataCancellazioneIsNullAndDataArchiviazioneIsNull(Integer id);
}
