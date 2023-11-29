package com.tallerwebi.dominio.repositorio;

import com.tallerwebi.dominio.modelo.Partido;

import java.util.List;

public interface RepositorioPartido {
    Partido buscar (Long id);
    Long guardar(Partido partido);
    void actualizar(Partido partido);
    List<Partido> listAll();


}
