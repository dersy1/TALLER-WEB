package com.tallerwebi.dominio.repositorio;

import com.tallerwebi.dominio.modelo.Equipo;

import java.util.List;

public interface RepositorioEquipo {
    Equipo buscar (Long id);
    Equipo buscarPorNombre (String id);
    void crear(Equipo equipo);
    List<Equipo> listAll();
}
