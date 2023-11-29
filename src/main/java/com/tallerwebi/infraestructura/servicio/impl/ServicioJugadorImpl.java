package com.tallerwebi.infraestructura.servicio.impl;

import com.tallerwebi.dominio.modelo.Jugador;
import com.tallerwebi.dominio.repositorio.RepositorioJugador;
import com.tallerwebi.infraestructura.servicio.ServicioJugador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ServicioJugadorImpl implements ServicioJugador {

    private RepositorioJugador repositorioJugador;

    @Autowired
    public ServicioJugadorImpl(RepositorioJugador repositorioJugador) {
        this.repositorioJugador = repositorioJugador;
    }

    @Override
    public Jugador buscarJugador(String nombre) {
        return this.repositorioJugador.buscar(nombre);
    }

}



