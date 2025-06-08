package com.geolocalizzazione.geolocalizzazione.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "automezzo")
public class Automezzo {
    @Id
    @Column(name = "numero", nullable = false)  // Il nome della colonna può essere personalizzato
    private Integer numero;

    @Column(nullable = false, length = 50,name ="marca")
    private String marca;

    @Column(nullable = false, length = 50, name="modello")
    private String modello;

    @Column(length = 12, name ="targa",nullable = false)
    private String targa;

    @Column(name ="patente_richiesta_id",nullable = false)
    private Integer patente_richiesta_id;

    @Column(name ="fk_tipo_veicolo")
    private Integer fkTipoVeicolo;

    @Column(name="satellitare")
    private Boolean satellitare;

    @Column(name="data_inserimento")
    private Date dataInserimento;

    @Column(name="data_modifica")
    private Date dataModifica;

    @Column(name="data_cancellazione")
    private Date dataCancellazione;

    @Column(name="data_archiviazione")
    private Date dataArchiviazione;


    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModello() {
        return modello;
    }

    public void setModello(String modello) {
        this.modello = modello;
    }

    public String getTarga() {
        return targa;
    }

    public void setTarga(String targa) {
        this.targa = targa;
    }

    public Integer getPatente_richiesta_id() {
        return patente_richiesta_id;
    }

    public void setPatente_richiesta_id(Integer patente_richiesta_id) {
        this.patente_richiesta_id = patente_richiesta_id;
    }

    public Boolean getSatellitare() {
        return satellitare;
    }

    public void setSatellitare(Boolean satellitare) {
        this.satellitare = satellitare;
    }

    public Date getDataInserimento() {
        return dataInserimento;
    }

    public void setDataInserimento(Date dataInserimento) {
        this.dataInserimento = dataInserimento;
    }

    public Date getDataModifica() {
        return dataModifica;
    }

    public void setDataModifica(Date dataModifica) {
        this.dataModifica = dataModifica;
    }

    public Date getDataCancellazione() {
        return dataCancellazione;
    }

    public void setDataCancellazione(Date dataCancellazione) {
        this.dataCancellazione = dataCancellazione;
    }

    public Date getDataArchiviazione() {
        return dataArchiviazione;
    }

    public void setDataArchiviazione(Date dataArchiviazione) {
        this.dataArchiviazione = dataArchiviazione;
    }

    public Integer getFkTipoVeicolo() {
        return fkTipoVeicolo;
    }

    public void setFkTipoVeicolo(Integer fkTipoVeicolo) {
        this.fkTipoVeicolo = fkTipoVeicolo;
    }
}
