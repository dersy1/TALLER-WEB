package com.tallerwebi.infraestructura.servicio.impl;


import com.tallerwebi.dominio.modelo.Partido;
import com.tallerwebi.dominio.modelo.Equipo;
import com.tallerwebi.dominio.repositorio.RepositorioPartido;
import com.tallerwebi.infraestructura.servicio.ServicioEquipo;
import com.tallerwebi.infraestructura.servicio.ServicioPartido;
import com.tallerwebi.presentacion.PartidoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class ServicioPartidoImpl implements ServicioPartido {
    @Autowired
    RepositorioPartido repositorioPartido;
    @Autowired
    ServicioEquipo servicioEquipo;

    Integer accionPc = 0;
    Boolean verificacion = false;
    String tipoAccion = "";

    @Autowired
    public ServicioPartidoImpl(RepositorioPartido repositorioPartido, ServicioEquipo servicioEquipo) {
        this.repositorioPartido = repositorioPartido;
        this.servicioEquipo = servicioEquipo;
    }


    @Override
    public Partido buscarPartido(Long id) {
        return this.repositorioPartido.buscar(id);
    }

    @Override
    public List<Partido> listAll() {
        return repositorioPartido.listAll();
    }

    @Override
    public Long inicializarPartido(Long idEquipo1, Long idEquipo2) {
        Partido partido = new Partido();
        partido.setPuntosPc(0);
        partido.setPuntosUsuario(0);
        Equipo equipoJugador = servicioEquipo.buscarEquipo(idEquipo1);
        Equipo equipoPc = servicioEquipo.buscarEquipo(idEquipo2);
        partido.setEquipoPc(equipoPc);
        partido.setEquipoJugador(equipoJugador);
        partido.setGuardable(true);
        this.accionPc = 0;
        this.tipoAccion = "";
        this.verificacion = false;
        return repositorioPartido.guardar(partido);
    }

    @Override
    public PartidoDTO guardarResultados(Integer puntajeYo, Integer puntajeRival) {
        PartidoDTO partido = new PartidoDTO();
        partido.setPuntajeJugador(puntajeYo);
        partido.setPuntajePc(puntajeRival);
        return partido;
    }

    @Override
    public void actualizarPartido(Partido partido) {
        repositorioPartido.actualizar(partido);
    }

    @Override
    public Integer elegirAccionPc(Integer posicion) {
        if (posicion == 1) {
            this.accionPc = 2;
        } else {
            Random rand = new Random();
            this.accionPc = rand.nextInt(3) + 1;
        }
        return this.accionPc;
    }
    @Override
    public void tirarDado(String tipoDeAccion, PartidoDTO partido) {
        Random rand = new Random();
        if (tipoDeAccion.equals("tirar") || tipoDeAccion.equals("pasar") || tipoDeAccion.equals("driblear")) {
            Integer dadoJugador = rand.nextInt(20) + 1;
            partido.setDadoJugador(dadoJugador);
            Integer dadoPC = rand.nextInt(15) + 1;
            partido.setDadoPC(dadoPC);
        } else {
            Integer dadoJugador = rand.nextInt(15) + 1;
            Integer dadoPC = rand.nextInt(20) + 1;
            partido.setDadoJugador(dadoJugador);
            partido.setDadoPC(dadoPC);
        }
    }

    @Override
    public Boolean compararStats(Integer dadoJugador, Integer dadoPC, String accion, Long idEquipo1, Long idEquipo2, Integer jugador, Integer posicion, List<String> itemsTrue) {
        Boolean resultado = false;
        switch (accion) {
            case "driblear":
                resultado = driblearStats(dadoJugador, dadoPC, idEquipo1, idEquipo2, jugador, accion, itemsTrue);
                break;
            case "tirar":
                resultado = tirarStats(dadoJugador, dadoPC, idEquipo1, idEquipo2, jugador, accion, itemsTrue);
                break;
            case "pasar":
                resultado = pasarStats(dadoJugador, dadoPC, idEquipo1, idEquipo2, jugador,accion, itemsTrue);
                break;
            case "robar":
                resultado = robarStats(dadoJugador, dadoPC, idEquipo1, idEquipo2, jugador, posicion, itemsTrue);
                break;
            case "tapar":
                resultado = taparStats(dadoJugador, dadoPC, idEquipo1, idEquipo2, jugador, posicion, itemsTrue);
                break;
            case "interceptar":
                resultado = interceptarStats(dadoJugador, dadoPC, idEquipo1, idEquipo2, jugador, posicion, itemsTrue);
                break;
            default:
                break;
        }
        return resultado;
    }
    @Override
    public Boolean driblearStats(Integer dadoJugador, Integer dadoPC, Long idEquipo1, Long idEquipo2, Integer jugador, String accion, List<String> itemsTrue) {
        Integer tienePotenciador = 0;
        for (String item: itemsTrue) {
            if(item.equals("drible")){
                tienePotenciador += 5;
            }
        }
        Integer statJugador = 0;
        Integer statPc = 0;
        this.accionPc = 0;
        if (jugador == 1) {
            statJugador = servicioEquipo.buscarEquipo(idEquipo1).getJugador1().getDrible() + dadoJugador + tienePotenciador;
            statPc = servicioEquipo.buscarEquipo(idEquipo2).getJugador1().getRobo() + dadoPC;
            adivinoAccionDeLaMaquina(false);
        } else {
            statJugador = servicioEquipo.buscarEquipo(idEquipo1).getJugador2().getDrible() + dadoJugador + tienePotenciador;
            statPc = servicioEquipo.buscarEquipo(idEquipo2).getJugador2().getRobo() + dadoPC;
            adivinoAccionDeLaMaquina(false);
        }
        if ( statJugador > statPc){
            this.tipoAccion =  accion;
        }else {
            this.tipoAccion = "robar";
        }
        return statJugador > statPc;
    }
    @Override
    public Boolean tirarStats(Integer dadoJugador, Integer dadoPC, Long idEquipo1, Long idEquipo2, Integer jugador, String accion,  List<String> itemsTrue) {
        Integer tienePotenciador = 0;
        for (String item: itemsTrue) {
            if(item.equals("tirar")){
                tienePotenciador += 5;
            }
        }
        Integer statJugador = 0;
        Integer statPc = 0;
        this.accionPc = 0;
        if (jugador == 1) {
            statJugador = servicioEquipo.buscarEquipo(idEquipo1).getJugador1().getTiro() + dadoJugador + tienePotenciador;
            statPc = servicioEquipo.buscarEquipo(idEquipo2).getJugador1().getTapa() + dadoPC;
            adivinoAccionDeLaMaquina(false);
        } else {
            statJugador = servicioEquipo.buscarEquipo(idEquipo1).getJugador2().getTiro() + dadoJugador + tienePotenciador;
            statPc = servicioEquipo.buscarEquipo(idEquipo2).getJugador2().getTapa() + dadoPC;
            adivinoAccionDeLaMaquina(false);
        }
        if ( statJugador > statPc){
            this.tipoAccion =  "";
        }else {
            this.tipoAccion = "tapar";
        }
        return statJugador > statPc;
    }
    @Override
    public Boolean pasarStats(Integer dadoJugador, Integer dadoPC, Long idEquipo1, Long idEquipo2, Integer jugador, String accion,  List<String> itemsTrue) {
        Integer tienePotenciador = 0;
        for (String item: itemsTrue) {
            if(item.equals("pasar")){
                tienePotenciador += 5;
            }
        }
        Integer statJugador = 0;
        Integer statPc = 0;
        this.accionPc = 0;
        if (jugador == 1) {
            statJugador = servicioEquipo.buscarEquipo(idEquipo1).getJugador1().getPase() + dadoJugador + tienePotenciador;
            statPc = servicioEquipo.buscarEquipo(idEquipo2).getJugador1().getIntercepcion() + dadoPC;
            adivinoAccionDeLaMaquina(false);
        } else {
            statJugador = servicioEquipo.buscarEquipo(idEquipo1).getJugador2().getPase() + dadoJugador + tienePotenciador;
            statPc = servicioEquipo.buscarEquipo(idEquipo2).getJugador2().getIntercepcion() + dadoPC;
            adivinoAccionDeLaMaquina(false);
        }
        if ( statJugador > statPc){
            this.tipoAccion =  accion;
        }else {
            this.tipoAccion = "interceptar";
        }
        return statJugador > statPc;
    }
    @Override
    public Boolean robarStats(Integer dadoJugador, Integer dadoPC, Long idEquipo1, Long idEquipo2, Integer jugador, Integer posicion, List<String> itemsTrue) {
        Integer tienePotenciador = 0;
        for (String item: itemsTrue) {
            if(item.equals("robar")){
                tienePotenciador += 5;
            }
        }
        Integer statJugador = 0;
        Integer statPc = 0;
        this.accionPc = elegirAccionPc(posicion);
        if(accionPc == 2 && posicion > 2){
            Random rand = new Random();
            this.accionPc = (rand.nextInt(2) == 0) ? 1 : 3;
        }
        if (jugador == 1) {
            if (accionPc == 1) {
                statJugador = servicioEquipo.buscarEquipo(idEquipo1).getJugador1().getRobo() + dadoJugador + 5 + tienePotenciador;
                statPc = servicioEquipo.buscarEquipo(idEquipo2).getJugador1().getDrible() + dadoPC;
                adivinoAccionDeLaMaquina(true);
            } else if (accionPc == 2) {
                statJugador = servicioEquipo.buscarEquipo(idEquipo1).getJugador1().getTapa() + dadoJugador + tienePotenciador;
                statPc = servicioEquipo.buscarEquipo(idEquipo2).getJugador1().getTiro() + dadoPC;
                adivinoAccionDeLaMaquina(false);
            } else {
                statJugador = servicioEquipo.buscarEquipo(idEquipo1).getJugador1().getIntercepcion() + dadoJugador + tienePotenciador;
                statPc = servicioEquipo.buscarEquipo(idEquipo2).getJugador1().getPase() + dadoPC;
                adivinoAccionDeLaMaquina(false);
            }
        } else {
            if (accionPc == 1) {
                statJugador = servicioEquipo.buscarEquipo(idEquipo1).getJugador2().getRobo() + dadoJugador + 5 + tienePotenciador;
                statPc = servicioEquipo.buscarEquipo(idEquipo2).getJugador2().getDrible() + dadoPC;
                adivinoAccionDeLaMaquina(true);
            } else if (accionPc == 2) {
                statJugador = servicioEquipo.buscarEquipo(idEquipo1).getJugador2().getTapa() + dadoJugador + tienePotenciador;
                statPc = servicioEquipo.buscarEquipo(idEquipo2).getJugador2().getTiro() + dadoPC;
                adivinoAccionDeLaMaquina(false);
            } else {
                statJugador = servicioEquipo.buscarEquipo(idEquipo1).getJugador2().getIntercepcion() + dadoJugador + tienePotenciador;
                statPc = servicioEquipo.buscarEquipo(idEquipo2).getJugador2().getPase() + dadoPC;
                adivinoAccionDeLaMaquina(false);
            }
        }
        Boolean resultado = statJugador > statPc;
        tipoAccionPc(accionPc, resultado);

        return resultado;
    }

    @Override
    public Boolean interceptarStats(Integer dadoJugador, Integer dadoPC, Long idEquipo1, Long idEquipo2, Integer jugador, Integer posicion,  List<String> itemsTrue) {
        Integer tienePotenciador = 0;
        for (String item: itemsTrue) {
            if(item.equals("tirar")){
                tienePotenciador += 5;
            }
        }
        Integer statJugador = 0;
        Integer statPc = 0;
        this.accionPc = elegirAccionPc(posicion);
        if(accionPc == 2 && posicion > 2){
            Random rand = new Random();
            this.accionPc = (rand.nextInt(2) == 0) ? 1 : 3;
        }
        if (jugador == 1) {
            if (accionPc == 3) {
                statJugador = servicioEquipo.buscarEquipo(idEquipo1).getJugador1().getIntercepcion() + dadoJugador + 5 + tienePotenciador;
                statPc = servicioEquipo.buscarEquipo(idEquipo2).getJugador1().getPase() + dadoPC;
                adivinoAccionDeLaMaquina(true);
            } else if (accionPc == 2) {
                statJugador = servicioEquipo.buscarEquipo(idEquipo1).getJugador1().getTapa() + dadoJugador + tienePotenciador;
                statPc = servicioEquipo.buscarEquipo(idEquipo2).getJugador1().getTiro() + dadoPC;
                adivinoAccionDeLaMaquina(false);
            } else {
                statJugador = servicioEquipo.buscarEquipo(idEquipo1).getJugador1().getRobo() + dadoJugador + tienePotenciador;
                statPc = servicioEquipo.buscarEquipo(idEquipo2).getJugador1().getDrible() + dadoPC;
                adivinoAccionDeLaMaquina(false);
            }
        } else {
            if (accionPc == 3) {
                statJugador = servicioEquipo.buscarEquipo(idEquipo1).getJugador2().getIntercepcion() + dadoJugador + 5 + tienePotenciador;
                statPc = servicioEquipo.buscarEquipo(idEquipo2).getJugador2().getPase() + dadoPC;
                adivinoAccionDeLaMaquina(true);
            } else if (accionPc == 2) {
                statJugador = servicioEquipo.buscarEquipo(idEquipo1).getJugador2().getTapa() + dadoJugador + tienePotenciador;
                statPc = servicioEquipo.buscarEquipo(idEquipo2).getJugador2().getTiro() + dadoPC;
                adivinoAccionDeLaMaquina(false);
            } else {
                statJugador = servicioEquipo.buscarEquipo(idEquipo1).getJugador2().getRobo() + dadoJugador + tienePotenciador;
                statPc = servicioEquipo.buscarEquipo(idEquipo2).getJugador2().getDrible() + dadoPC;
                adivinoAccionDeLaMaquina(false);
            }
        }
        Boolean resultado = statJugador > statPc;
        tipoAccionPc(accionPc, resultado);

        return resultado;
    }
    @Override
    public Boolean taparStats(Integer dadoJugador, Integer dadoPC, Long idEquipo1, Long idEquipo2, Integer jugador, Integer posicion,  List<String> itemsTrue) {
        Integer tienePotenciador = 0;
        for (String item: itemsTrue) {
            if(item.equals("tirar")){
                tienePotenciador += 5;
            }
        }
        Integer statJugador = 0;
        Integer statPc = 0;
        this.accionPc = elegirAccionPc(posicion);
        if(accionPc == 2 && posicion > 2){
            Random rand = new Random();
            this.accionPc = (rand.nextInt(2) == 0) ? 1 : 3;
        }
        if (jugador == 1) {
            if (accionPc == 2) {
                statJugador = servicioEquipo.buscarEquipo(idEquipo1).getJugador1().getTapa() + dadoJugador + 5 + tienePotenciador;
                statPc = servicioEquipo.buscarEquipo(idEquipo2).getJugador1().getTiro() + dadoPC;
                adivinoAccionDeLaMaquina(true);
            } else if (accionPc == 1) {
                statJugador = servicioEquipo.buscarEquipo(idEquipo1).getJugador1().getRobo() + dadoJugador + tienePotenciador;
                statPc = servicioEquipo.buscarEquipo(idEquipo2).getJugador1().getDrible() + dadoPC;
                adivinoAccionDeLaMaquina(false);
            } else {
                statJugador = servicioEquipo.buscarEquipo(idEquipo1).getJugador1().getIntercepcion() + dadoJugador + tienePotenciador;
                statPc = servicioEquipo.buscarEquipo(idEquipo2).getJugador1().getPase() + dadoPC;
                adivinoAccionDeLaMaquina(false);
            }
        } else {
            if (accionPc == 2) {
                statJugador = servicioEquipo.buscarEquipo(idEquipo1).getJugador2().getTapa() + dadoJugador + 5 + tienePotenciador;
                statPc = servicioEquipo.buscarEquipo(idEquipo2).getJugador2().getTiro() + dadoPC;
                adivinoAccionDeLaMaquina(true);
            } else if (accionPc == 1) {
                statJugador = servicioEquipo.buscarEquipo(idEquipo1).getJugador2().getRobo() + dadoJugador + tienePotenciador;
                statPc = servicioEquipo.buscarEquipo(idEquipo2).getJugador2().getDrible() + dadoPC;
                adivinoAccionDeLaMaquina(false);
            } else {
                statJugador = servicioEquipo.buscarEquipo(idEquipo1).getJugador2().getIntercepcion() + dadoJugador + tienePotenciador;
                statPc = servicioEquipo.buscarEquipo(idEquipo2).getJugador2().getPase() + dadoPC;
                adivinoAccionDeLaMaquina(false);
            }
        }
        Boolean resultado = statJugador > statPc;
        tipoAccionPc(accionPc, resultado);

        return resultado;
    }
    @Override
    public void tipoAccionPc(Integer accion, Boolean resultado){
        if(resultado && accion == 1){
            this.tipoAccion = "robar";
        }else if (!resultado && accion == 1){
            this.tipoAccion = "driblear";
        }
        if(resultado && accion == 2){
            this.tipoAccion = "tapar";
        }else if (!resultado && accion == 2){
            this.tipoAccion = "tirar";
        }
        if(resultado && accion == 3){
            this.tipoAccion = "interceptar";
        }else if (!resultado && accion == 3){
            this.tipoAccion = "pasar";
        }
    }

    @Override
    public Boolean adivinoAccionDeLaMaquina(Boolean adivino) {
        this.verificacion = adivino;
        return verificacion;
    }

    @Override
    public Boolean getVerificacion() {
        return this.verificacion;
    }

    @Override
    public String getAccion() {
        return this.tipoAccion;
    }

    @Override
    public void actualizar(Long id) {
        repositorioPartido.actualizar(buscarPartido(id));
    }
    @Override
    public void guardarPuntajeFinal(Long id, PartidoDTO partidoDTO) {
        Partido partido=buscarPartido(id);
        partido.setPuntosUsuario(partidoDTO.getPuntajeJugador());
        partido.setPuntosPc(partidoDTO.getPuntajePc());
        partido.setGuardable(false);
        actualizar(id);
    }

    @Override
    public void guardarPartido(Long id, PartidoDTO partidoDTO) {
        Partido partido=buscarPartido(id);
        partido.setPuntosUsuario(partidoDTO.getPuntajeJugador());
        partido.setPuntosPc(partidoDTO.getPuntajePc());
        partido.setEquipoJugador(partidoDTO.getEquipoJugador());
        partido.setEquipoPc(partidoDTO.getEquipoPC());
        partido.setPosicion(partidoDTO.getPosicion());
        partido.setTengoLaPelota(partidoDTO.getTengoLaPelota());
        partido.setTienePelotaJugador(partidoDTO.getTienePelotaJugador());
        actualizar(id);
    }

    @Override
    public Long buscarPartidoGuardado() {
        List<Partido> partidos = repositorioPartido.listAll();
        for (Partido partido : partidos) {
            if (partido.getGuardable()) {
                return partido.getId();
            }
        }
        return null;
    }

    @Override
    public String retornarAccionPc() {
        String accion = "";
        if (this.accionPc == 1) {
            accion = "driblear";
        } else if (this.accionPc == 2) {
            accion = "tirar";
        } else if (this.accionPc == 3) {
            accion = "pasar";
        }
        return accion;
    }

}
