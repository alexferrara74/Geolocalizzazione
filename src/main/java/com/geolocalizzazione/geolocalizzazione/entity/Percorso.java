package com.geolocalizzazione.geolocalizzazione.entity;

import com.geolocalizzazione.geolocalizzazione.converter.StringListConverter;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "percorso") // Sostituisci con il nome reale della tabella
public class Percorso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "veicolo_numero")
    private Integer fkVeicolo;

    @Column(name = "id_autista")
    private Integer idAutista;

    @Column(name = "id_punto_partenza")
    private Integer idPuntoPartenza;

    @Column(name = "distanza_percorsa")
    private Double distanzaPercorsa;

    @Convert(converter = StringListConverter.class)
    @Column(columnDefinition = "TEXT")
    private List<String> shape;

    @Column(name = "tempo_impiegato")
    private Double tempoImpiegato; // In secondi, minuti, o millisecondi? Dipende da te.

    @Column(name = "data_cancellazione")
    private Date dataCancellazione;

    @Column(name = "data_creazione")
    private Date dataCreazione;

    @Column(name = "data_archiviazione")
    private Date dataArchiviazione;


    // Getters e Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFkVeicolo() {
        return fkVeicolo;
    }

    public void setFkVeicolo(Integer veicoloNumero) {
        this.fkVeicolo = veicoloNumero;
    }

    public Integer getIdAutista() {
        return idAutista;
    }

    public void setIdAutista(Integer idAutista) {
        this.idAutista = idAutista;
    }

    public Integer getIdPuntoPartenza() {
        return idPuntoPartenza;
    }

    public void setIdPuntoPartenza(Integer idPuntoPartenza) {
        this.idPuntoPartenza = idPuntoPartenza;
    }

    public Double getDistanzaPercorsa() {
        return distanzaPercorsa;
    }

    public void setDistanzaPercorsa(Double distanzaPercorsa) {
        this.distanzaPercorsa = distanzaPercorsa;
    }

    public List<String> getShape() {
        return shape;
    }

    public void setShape(List<String> shape) {
        this.shape = shape;
    }

    public Double getTempoImpiegato() {
        return tempoImpiegato;
    }

    public void setTempoImpiegato(Double tempoImpiegato) {
        this.tempoImpiegato = tempoImpiegato;
    }

    public Date getDataCancellazione() {
        return dataCancellazione;
    }

    public void setDataCancellazione(Date dataCancellazione) {
        this.dataCancellazione = dataCancellazione;
    }

    public Date getDataCreazione() {
        return dataCreazione;
    }

    public void setDataCreazione(Date dataCreazione) {
        this.dataCreazione = dataCreazione;
    }

    public Date getDataArchiviazione() {
        return dataArchiviazione;
    }

    public void setDataArchiviazione(Date dataArchiviazione) {
        this.dataArchiviazione = dataArchiviazione;
    }
}
