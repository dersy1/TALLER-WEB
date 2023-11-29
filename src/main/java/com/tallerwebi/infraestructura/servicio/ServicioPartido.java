package com.tallerwebi.infraestructura.servicio;

import com.tallerwebi.dominio.modelo.Partido;
import com.tallerwebi.presentacion.PartidoDTO;

import java.util.List;

public interface ServicioPartido {
    Partido buscarPartido(Long id);
    List<Partido> listAll();

    Long inicializarPartido(Long idEquipo1,Long idEquipo2);

    PartidoDTO guardarResultados(Integer puntajeYo, Integer puntajeRival);

    void actualizarPartido(Partido partido);

    Boolean compararStats(Integer dadoJugador, Integer dadoPc, String accion, Long idEquipo1, Long idEquipo2, Integer jugador, Integer posicion, List<String> itemsTrue);

    void guardarPuntajeFinal(Long id, PartidoDTO partidoDTO);

    String retornarAccionPc();

    void tirarDado(String tipoDeAccion, PartidoDTO partido);

    void guardarPartido(Long id, PartidoDTO partido);

    Long buscarPartidoGuardado();

    void actualizar(Long id);
    Integer elegirAccionPc(Integer posicion);

    Boolean driblearStats(Integer dadoJugador, Integer dadoPC, Long idEquipo1, Long idEquipo2, Integer jugador, String accion, List<String> itemsTrue);
    Boolean robarStats(Integer dadoJugador, Integer dadoPC, Long idEquipo1, Long idEquipo2, Integer jugador, Integer posicion, List<String> itemsTrue);

    Boolean tirarStats(Integer dadoJugador, Integer dadoPC, Long idEquipo1, Long idEquipo2, Integer jugador, String accion, List<String> itemsTrue);

    Boolean pasarStats(Integer dadoJugador, Integer dadoPC, Long idEquipo1, Long idEquipo2, Integer jugador, String accion, List<String> itemsTrue);

    Boolean interceptarStats(Integer dadoJugador, Integer dadoPC, Long idEquipo1, Long idEquipo2, Integer jugador, Integer posicion, List<String> itemsTrue);

    Boolean taparStats(Integer dadoJugador, Integer dadoPC, Long idEquipo1, Long idEquipo2, Integer jugador, Integer posicion, List<String> itemsTrue);

    Boolean adivinoAccionDeLaMaquina(Boolean adivino);

    Boolean getVerificacion();

    String getAccion();

    void tipoAccionPc(Integer accion, Boolean resultado);
}
