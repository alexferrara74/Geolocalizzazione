package com.geolocalizzazione.geolocalizzazione.repository;
import com.geolocalizzazione.geolocalizzazione.entity.TipoVeicolo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoVeicoliRepository extends JpaRepository<TipoVeicolo, Integer> {
    TipoVeicolo findByKey(String key);
}
