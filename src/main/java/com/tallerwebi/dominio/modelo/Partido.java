package com.tallerwebi.dominio.modelo;

import com.tallerwebi.dominio.modelo.Equipo;

import javax.persistence.*;

@Entity
public class Partido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer puntosUsuario;

    private Integer puntosPc;

    @OneToOne
    private Equipo equipoJugador;

    @OneToOne
    private Equipo equipoPc;

    private Integer posicion;

    private Boolean tengoLaPelota;

    private Integer tienePelotaJugador;

    private Boolean guardable = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPuntosUsuario() {
        return puntosUsuario;
    }

    public void setPuntosUsuario(Integer puntosUsuario) {
        this.puntosUsuario = puntosUsuario;
    }

    public Integer getPuntosPc() {
        return puntosPc;
    }

    public void setPuntosPc(Integer puntosPc) {
        this.puntosPc = puntosPc;
    }

    public Equipo getEquipoJugador() {
        return equipoJugador;
    }

    public void setEquipoJugador(Equipo equipoJugador) {
        this.equipoJugador = equipoJugador;
    }

    public Equipo getEquipoPc() {
        return equipoPc;
    }

    public void setEquipoPc(Equipo equipoPc) {
        this.equipoPc = equipoPc;
    }

    public Integer getPosicion() {
        return posicion;
    }

    public void setPosicion(Integer posicion) {
        this.posicion = posicion;
    }

    public Boolean getTengoLaPelota() {
        return tengoLaPelota;
    }

    public void setTengoLaPelota(Boolean tengoLaPelota) {
        this.tengoLaPelota = tengoLaPelota;
    }

    public Integer getTienePelotaJugador() {
        return tienePelotaJugador;
    }

    public void setTienePelotaJugador(Integer tienePelotaJugador) {
        this.tienePelotaJugador = tienePelotaJugador;
    }

    public Boolean getGuardable() {
        return guardable;
    }

    public void setGuardable(Boolean guardable) {
        this.guardable = guardable;
    }
}
