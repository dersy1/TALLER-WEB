package com.tallerwebi.dominio.repositorio;

import com.tallerwebi.dominio.modelo.Jugador;
import org.springframework.stereotype.Repository;

@Repository("repositorioJugador")
public interface RepositorioJugador {
    Jugador buscar(String nombre);
    void guardar(Jugador jugador);
}

