package com.geolocalizzazione.geolocalizzazione.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tipo_veicolo")
public class TipoVeicolo {

    @Id
    @Column(name = "id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "key", length = 100)
    private String key;

    @Column(name = "value", length = 100)
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
