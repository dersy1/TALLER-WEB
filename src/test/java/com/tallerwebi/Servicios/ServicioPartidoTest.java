package com.tallerwebi.Servicios;

import com.tallerwebi.dominio.repositorio.RepositorioPartido;
import com.tallerwebi.infraestructura.servicio.ServicioEquipo;
import com.tallerwebi.infraestructura.servicio.ServicioPartido;
import com.tallerwebi.infraestructura.servicio.ServicioTienda;
import com.tallerwebi.infraestructura.servicio.impl.ServicioPartidoImpl;
import com.tallerwebi.presentacion.PartidoDTO;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;
import com.tallerwebi.dominio.modelo.Partido;

import javax.servlet.http.Part;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;

public class ServicioPartidoTest {

    RepositorioPartido repositorioPartido = mock(RepositorioPartido.class);
    ServicioEquipo servicioEquipo = mock(ServicioEquipo.class);
    ServicioPartido servicioPartido  = new ServicioPartidoImpl(repositorioPartido, servicioEquipo);

    @Test
    public void siExisteUnaPartidaGuardadaSeRetoma(){
        List<Partido> partidos = new ArrayList<>();

        Partido partidoGuardable = new Partido();
        partidoGuardable.setId(1L);
        partidoGuardable.setGuardable(true);
        partidos.add(partidoGuardable);

        Partido partidoNoGuardable = new Partido();
        partidoNoGuardable.setId(2L);
        partidoNoGuardable.setGuardable(false);
        partidos.add(partidoNoGuardable);

        Long id = whenExisteUnaPartidaGuardada(partidos);
        thenSeRetoma(id);
    }
    private Long whenExisteUnaPartidaGuardada(List<Partido> partidos) {
        when(repositorioPartido.listAll()).thenReturn(partidos);
        return servicioPartido.buscarPartidoGuardado();
    }
    private void thenSeRetoma(Long id){
        assertThat(id, equalTo(1L));
    }

    @Test
    public void seGuardaElPuntajeFinalDeUnPartido(){
        Partido partido = new Partido();
        partido.setId(1L);
        PartidoDTO partidoDTO = new PartidoDTO();
        partidoDTO.setIdPartido(1L);
        partidoDTO.setPuntajePc(21);
        partidoDTO.setPuntajeJugador(19);
        whenGuardoPuntajeFinal(partidoDTO.getIdPartido(),partidoDTO, partido);
        thenSeActualizaElPartido(partido);
    }


    private void whenGuardoPuntajeFinal(Long id, PartidoDTO partidoDTO, Partido partido) {
        when(servicioPartido.buscarPartido(id)).thenReturn(partido);
        servicioPartido.guardarPuntajeFinal(id,partidoDTO);
    }
    private void thenSeActualizaElPartido(Partido partido) {
        verify(repositorioPartido, times(1)).actualizar(partido);
        assertThat(partido.getPuntosPc(), equalTo(21));
        assertThat(partido.getPuntosUsuario(), equalTo(19));
    }
}
