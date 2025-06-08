package com.geolocalizzazione.geolocalizzazione.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "percorso_poi") // Sostituisci con il nome reale della tabella
public class PercorsoPoi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_percorso",nullable = false)
    private Integer fkPercorso;

    @Column(name = "id_poi",nullable = false)
    private Integer fkPoi;

    @Column(name = "ordine",nullable = false)
    private Integer ordine;

    @Column(name = "data_archiviazione",nullable = true)
    private Date dataArchiviazione;

    @Column(name = "data_creazione",nullable = true)
    private Date dataInserimento;

    @Column(name = "data_cancellazione",nullable = true)
    private Date dataCancellazione;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFkPercorso() {
        return fkPercorso;
    }

    public void setFkPercorso(Integer fkPercorso) {
        this.fkPercorso = fkPercorso;
    }

    public Integer getFkPoi() {
        return fkPoi;
    }

    public void setFkPoi(Integer fkPoi) {
        this.fkPoi = fkPoi;
    }

    public Integer getOrdine() {
        return ordine;
    }

    public void setOrdine(Integer ordine) {
        this.ordine = ordine;
    }

    public Date getDataArchiviazione() {
        return dataArchiviazione;
    }

    public void setDataArchiviazione(Date dataArchiviazione) {
        this.dataArchiviazione = dataArchiviazione;
    }

    public Date getDataInserimento() {
        return dataInserimento;
    }

    public void setDataInserimento(Date dataInserimento) {
        this.dataInserimento = dataInserimento;
    }

    public Date getDataCancellazione() {
        return dataCancellazione;
    }

    public void setDataCancellazione(Date dataCancellazione) {
        this.dataCancellazione = dataCancellazione;
    }
}
