package com.tallerwebi.infraestructura.servicio;

import com.tallerwebi.dominio.modelo.Equipo;

import java.util.List;

public interface ServicioEquipo {
    Equipo buscarEquipo(Long idEquipo);
    List<Equipo> listAll();

}
