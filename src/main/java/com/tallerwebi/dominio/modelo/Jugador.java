package com.tallerwebi.dominio.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Jugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    //meter todos los stats en una clase Stats?
    private Integer drible;
    private Integer tiro;
    private Integer pase;
    private Integer robo;
    private Integer tapa;
    private Integer intercepcion;
    private String imagen;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getDrible() {
        return drible;
    }

    public void setDrible(Integer drible) {
        this.drible = drible;
    }

    public void setTiro(Integer tiro) {this.tiro = tiro;}

    public Integer getTiro() {
        return tiro;
    }


    public Integer getPase() {
        return pase;
    }

    public void setPase(Integer pase) {
        this.pase = pase;
    }

    public Integer getRobo() {
        return robo;
    }

    public void setRobo(Integer robo) {
        this.robo = robo;
    }

    public Integer getTapa() {
        return tapa;
    }

    public void setTapa(Integer tapa) {
        this.tapa = tapa;
    }

    public Integer getIntercepcion() {
        return intercepcion;
    }

    public void setIntercepcion(Integer intercepcion) {
        this.intercepcion = intercepcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
