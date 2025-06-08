package com.geolocalizzazione.geolocalizzazione.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "autista")
public class Autista {

    @Id
    @Column(name = "id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome",nullable = false , length = 50)  // Il nome della colonna può essere personalizzato
    private String nome;

    @Column(name = "cognome",nullable = false , length = 50)  // Il nome della colonna può essere personalizzato
    private String cognome;

    @Column(name = "telefono",nullable = false , length = 15)  // Il nome della colonna può essere personalizzato
    private String telefono;

    @Column(name="data_inserimento")
    private Date dataInserimento;

    @Column(name="data_modifica")
    private Date dataModifica;

    @Column(name="data_cancellazione")
    private Date dataCancellazione;

    @Column(name="data_archiviazione")
    private Date dataArchiviazione;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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
}
