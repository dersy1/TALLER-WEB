package com.tallerwebi.Controladores;

import com.tallerwebi.dominio.modelo.Equipo;
import com.tallerwebi.dominio.modelo.Jugador;
import com.tallerwebi.dominio.modelo.Partido;
import com.tallerwebi.infraestructura.servicio.ServicioPartido;
import com.tallerwebi.infraestructura.servicio.ServicioTienda;
import com.tallerwebi.presentacion.ControladorPartida;
import com.tallerwebi.presentacion.PartidoDTO;
import org.dom4j.rule.Mode;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.parallel.Resources;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.OneToOne;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ControladorPartidaTest {
    ServicioTienda servicioTienda = mock(ServicioTienda.class);
    ServicioPartido servicioPartido = mock(ServicioPartido.class);
    ControladorPartida controladorPartida = new ControladorPartida(null,servicioPartido,null,servicioTienda);
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpSession session = mock(HttpSession.class);

    @Test
    public void siElPuntajeLlegaA21ElPartidoTermina(){

        Jugador jugador1 = new Jugador();
        jugador1.setId(1L);
        Jugador jugador2 = new Jugador();
        jugador2.setId(2L);
        Equipo equipo1 = new Equipo();
        equipo1.setIdEquipo(1L);
        equipo1.setJugador1(jugador1);
        equipo1.setJugador2(jugador2);
        Jugador jugador3 = new Jugador();
        jugador3.setId(3L);
        Jugador jugador4 = new Jugador();
        jugador4.setId(4L);
        Equipo equipo2 = new Equipo();
        equipo2.setIdEquipo(2L);
        equipo2.setJugador1(jugador3);
        equipo2.setJugador2(jugador4);
        PartidoDTO partido = new PartidoDTO();
        partido.setIdPartido(1L);
        partido.setPuntajeJugador(21);
        partido.setPuntajePc(19);
        partido.setEquipoPC(equipo1);
        partido.setEquipoJugador(equipo2);
        controladorPartida.setPartidoNuevo(partido);
        ModelAndView mav = whenElPartidoTermina(partido);
        thenDevuelveVistaPartidoResultado(mav);
    }

    private void thenDevuelveVistaPartidoResultado(ModelAndView mav) {
        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:partido-resultado?idPartido=" + 1));
    }


    private ModelAndView whenElPartidoTermina(PartidoDTO partido) {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("id")).thenReturn(1L);
        return controladorPartida.irAPartido(partido.getIdPartido(), request);

    }

    @Test
    public void siElPuntajeEsMenorQue21NoTermine(){
        Jugador jugador1 = new Jugador();
        jugador1.setId(1L);
        Jugador jugador2 = new Jugador();
        jugador2.setId(2L);
        Equipo equipo1 = new Equipo();
        equipo1.setIdEquipo(1L);
        equipo1.setJugador1(jugador1);
        equipo1.setJugador2(jugador2);
        Jugador jugador3 = new Jugador();
        jugador3.setId(3L);
        Jugador jugador4 = new Jugador();
        jugador4.setId(4L);
        Equipo equipo2 = new Equipo();
        equipo2.setIdEquipo(2L);
        equipo2.setJugador1(jugador3);
        equipo2.setJugador2(jugador4);
        PartidoDTO partido = new PartidoDTO();
        partido.setIdPartido(1L);
        partido.setPuntajeJugador(20);
        partido.setPuntajePc(19);
        partido.setEquipoJugador(equipo1);
        partido.setEquipoPC(equipo2);
        controladorPartida.setPartidoNuevo(partido);
        ModelAndView mav = whenElPartidoSigue(partido);
        thenDevuelveVistaPartido(mav);
    }
    private void thenDevuelveVistaPartido(ModelAndView mav) {
        assertThat(mav.getViewName(), equalToIgnoringCase("partido"));
    }


    private ModelAndView whenElPartidoSigue(PartidoDTO partido) {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("id")).thenReturn(1L);
        return controladorPartida.irAPartido(partido.getIdPartido(), request);
    }



    @Test
    public void siExisteUnaPartidaGuardadaSeRetoma(){
        ModelAndView mav =  whenExisteUnaPartidaGuardada();
        thenSeRetoma(mav);
    }
    private ModelAndView whenExisteUnaPartidaGuardada() {
        return controladorPartida.checkGuardado();
    }
    private void thenSeRetoma(ModelAndView mav){
        assertThat(mav.getViewName(), equalToIgnoringCase("partidaGuardada"));
    }

}