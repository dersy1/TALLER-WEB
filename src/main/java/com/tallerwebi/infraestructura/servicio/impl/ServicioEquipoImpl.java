package com.tallerwebi.infraestructura.servicio.impl;

import com.tallerwebi.dominio.modelo.Equipo;
import com.tallerwebi.dominio.modelo.Jugador;
import com.tallerwebi.dominio.repositorio.RepositorioEquipo;
import com.tallerwebi.dominio.repositorio.RepositorioJugador;
import com.tallerwebi.infraestructura.servicio.ServicioEquipo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ServicioEquipoImpl implements ServicioEquipo {

    @Autowired
    private RepositorioEquipo repositorioEquipo;
    @Autowired
    private RepositorioJugador repositorioJugador;

    @Autowired
    public ServicioEquipoImpl(RepositorioJugador repositorioJugador, RepositorioEquipo respositorioEquipo){
        this.repositorioEquipo = respositorioEquipo;
        this.repositorioJugador= repositorioJugador;
    }


    @Override
    public Equipo buscarEquipo(Long idEquipo) {
        return this.repositorioEquipo.buscar(idEquipo);
    }

    @Override
    public List<Equipo> listAll() {
        return repositorioEquipo.listAll();
    }


}
