package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.modelo.Equipo;
import com.tallerwebi.dominio.modelo.Inventario;
import com.tallerwebi.dominio.modelo.Partido;
import com.tallerwebi.infraestructura.servicio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorPartida {

    PartidoDTO partidoNuevo = new PartidoDTO();
    ItemsPartido items = new ItemsPartido();

    //Se pueden usar todos los servicios necesarios
    //private ServicioJugador servicioJugador;
    private ServicioEquipo servicioEquipo;
    private ServicioPartido servicioPartido;
    private ServicioInventario servicioInventario;
    private ServicioTienda servicioTienda;

    //Los controladores se comunica con los servicios y saben si estatodo bien gracias a las exceptions
    @Autowired
    public ControladorPartida(/*ServicioJugador servicioJugador,*/ ServicioEquipo servicioEquipo, ServicioPartido servicioPartido, ServicioInventario servicioInventario, ServicioTienda servicioTienda) {
        //this.servicioJugador = servicioJugador;
        this.servicioEquipo = servicioEquipo;
        this.servicioPartido = servicioPartido;
        this.servicioInventario = servicioInventario;
        this.servicioTienda = servicioTienda;
    }

    public Boolean verificar(@RequestParam(required = false) HttpServletRequest request){
        Long id = (Long) request.getSession().getAttribute("id");
        if(id == null){
            return true;
        }
        return false;
    }

    @RequestMapping(value = "/check-guardado")
    public ModelAndView checkGuardado() {
        ModelMap modelo = new ModelMap();
        Long hayGuardado = servicioPartido.buscarPartidoGuardado();
        modelo.put("hayGuardado", hayGuardado);
        if (hayGuardado != null) {
            return new ModelAndView("partidaGuardada", modelo);
        } else {
            return new ModelAndView("redirect:elegir-equipo");
        }
    }

    @RequestMapping(value = "/recuperar-partida", method = {RequestMethod.POST})
    public ModelAndView recuperarPartida(@RequestParam(required = true) Boolean respuesta) {
        Long idPartido = servicioPartido.buscarPartidoGuardado();
        if (respuesta) {
            return new ModelAndView("redirect:partido?idPartido=" + idPartido);
        } else {
            Partido partido = servicioPartido.buscarPartido(idPartido);
            partido.setGuardable(false);
            servicioPartido.actualizarPartido(partido);
            return new ModelAndView("redirect:elegir-equipo");
        }
    }

    @RequestMapping(value = "/elegir-equipo", method = {RequestMethod.GET})
    public ModelAndView elegirEquipo(@RequestParam(required = false) Long idEquipo1, @RequestParam(required = false) Long idEquipo2, HttpServletRequest request) {
        if (verificar(request)){
            return new ModelAndView("redirect:login");
        };
        ModelMap modelo = new ModelMap();
        modelo.put("equipos", servicioEquipo.listAll());
        modelo.put("idEquipo1", idEquipo1);
        modelo.put("idEquipo2", idEquipo2);
        modelo.put("equipo1", servicioEquipo.buscarEquipo(idEquipo1));
        modelo.put("equipo2", servicioEquipo.buscarEquipo(idEquipo2));
        items.vaciarTodos();

        return new ModelAndView("elegir-equipo", modelo);
    }

    @RequestMapping(value = "/seleccionarEquipo", method = {RequestMethod.POST})
    public ModelAndView seleccionarEquipo(@RequestParam(required = false) Long idEquipo1, @RequestParam(required = false) Long idEquipo2) {
        String redirect = "redirect:/elegir-equipo";
        if (idEquipo1 != null) {
            redirect += "?idEquipo1=" + idEquipo1;
        }
        if (idEquipo2 != null) {
            redirect += (idEquipo1 != null ? "&" : "?") + "idEquipo2=" + idEquipo2;
        }
        return new ModelAndView(redirect);
    }


    @RequestMapping(value = "/items", method = {RequestMethod.GET})
    public ModelAndView irAItems(@RequestParam(required = true) Long idEquipo1, @RequestParam(required = true) Long idEquipo2, HttpServletRequest request) {
        if (verificar(request)){
            return new ModelAndView("redirect:login");
        };
        List<Long> itemsFalse = items.traerLosFalse();
        List<Inventario> listaInventarios = new ArrayList<>();
        for (Long item : itemsFalse) {
            Inventario inventario = servicioInventario.buscar(item);
            if (inventario != null) {
                listaInventarios.add(inventario);
            }
        }
        ModelMap modelo = new ModelMap();
        //modelo.put("vacios", )
        modelo.put("items", listaInventarios);
        modelo.put("idEquipo1", idEquipo1);
        modelo.put("idEquipo2", idEquipo2);
        return new ModelAndView("partido-items", modelo);
    }

    @RequestMapping(value = "/equipar", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView equiparItems(@RequestParam(required = true) Long idEquipo1, @RequestParam(required = true) Long idEquipo2, @RequestParam String nombreProducto) {
        servicioInventario.consumir(servicioInventario.buscar(nombreProducto));
        items.setearTrue(nombreProducto);
        return new ModelAndView("redirect:items?idEquipo1=" + idEquipo1 + "&idEquipo2=" + idEquipo2);
    }

    @RequestMapping(value = "/iniciarPartida", method = {RequestMethod.GET})
    public ModelAndView iniciarPartida(@RequestParam(required = true) Long idEquipo1, @RequestParam(required = true) Long idEquipo2) {
        Long idPartido = servicioPartido.inicializarPartido(idEquipo1, idEquipo2);
        partidoNuevo = new PartidoDTO();
        partidoNuevo.setEquipoJugador(servicioPartido.buscarPartido(idPartido).getEquipoJugador());
        partidoNuevo.setEquipoPC(servicioPartido.buscarPartido(idPartido).getEquipoPc());
        partidoNuevo.setIdPartido(idPartido);
        partidoNuevo.getEquipoPC().getJugador1().setImagen("images/JUGADOR-VISITANTE.png");
        partidoNuevo.getEquipoPC().getJugador2().setImagen("images/JUGADOR-VISITANTE.png");
        partidoNuevo.getEquipoJugador().getJugador1().setImagen("images/JUGADOR-LOCAL-CON-PELOTA.png");
        partidoNuevo.getEquipoJugador().getJugador2().setImagen("images/JUGADOR-LOCAL.png");
        return new ModelAndView("redirect:partido?idPartido=" + idPartido);
    }


    @RequestMapping(path = "/partido", method = RequestMethod.GET)
    public ModelAndView irAPartido(@RequestParam(required = true) Long idPartido, HttpServletRequest request) {
        if (verificar(request)){
            return new ModelAndView("redirect:login");
        };
        //www.web.unlam.com/partido?idEquipo1=1
        //www.web.unlam.com/partido/1/2
        //@ModelAttribute= objeto entero
        //@PathVariable= solo el valor
        //@RequestParam= tambien
        ModelMap modelo = new ModelMap();
        modelo.put("partido", partidoNuevo);
        modelo.put("accionElegidaPc", servicioPartido.retornarAccionPc());
        modelo.put("adivinoLaAccionDeLaPc", servicioPartido.getVerificacion());
        modelo.put("accion", servicioPartido.getAccion());
        modelo.put("items", items);
        partidoNuevo.setImagenes();
        if (partidoNuevo.getPuntajeJugador() < 21 && partidoNuevo.getPuntajePc() < 21) {
            return new ModelAndView("partido", modelo);
        } else {
            return new ModelAndView("redirect:partido-resultado?idPartido=" + idPartido);
        }
    }


    @RequestMapping(value = "/acciones", method = RequestMethod.POST)
    public ModelAndView realizarAcciones(@RequestParam(required = true) String tipoAccion) {
        Long idPartido = partidoNuevo.getIdPartido();
        Integer jugador = partidoNuevo.getTienePelotaJugador();
        servicioPartido.tirarDado(tipoAccion, partidoNuevo);
        Integer dadoJugador = partidoNuevo.getDadoJugador();
        Integer dadoPC = partidoNuevo.getDadoPC();
        Boolean resultado = servicioPartido.compararStats(dadoJugador, dadoPC, tipoAccion, partidoNuevo.getEquipoJugador().getIdEquipo(), partidoNuevo.getEquipoPC().getIdEquipo(), jugador, partidoNuevo.getPosicion(), items.traerLosTrue());

        if (tipoAccion.equals("tirar")) {
            if (resultado) {
                partidoNuevo.setTengoLaPelota(true);
                return new ModelAndView("redirect:partido-aro?idPartido=" + idPartido);
            } else {
                partidoNuevo.setTengoLaPelota(false);
                return new ModelAndView("redirect:partido?idPartido=" + idPartido);
            }
        }
        if (resultado) {
            partidoNuevo.setTengoLaPelota(true);

            if (tipoAccion.equals("pasar")) {
                if (partidoNuevo.getTienePelotaJugador() == 1) {
                    partidoNuevo.setTienePelotaJugador(2);
                } else {
                    partidoNuevo.setTienePelotaJugador(1);
                }
            }
            return new ModelAndView("redirect:posicion?resultado=" + true + "&idPartido=" + idPartido);
        } else {
            partidoNuevo.setTengoLaPelota(false);
            verificaLaAccionPc(servicioPartido.retornarAccionPc());
            return new ModelAndView("redirect:posicion?resultado=" + false + "&idPartido=" + idPartido);
        }
    }

    private void verificaLaAccionPc(String accionPc) {
        if (accionPc.equals("tirar")) {
            int puntos = (partidoNuevo.getPosicion() == 1) ? 2 : 3;
            partidoNuevo.setPosicion(1);
            partidoNuevo.setTengoLaPelota(true);
            partidoNuevo.setTienePelotaJugador(1);
            partidoNuevo.setPuntajePc(partidoNuevo.getPuntajePc() + puntos);
        }

        if (accionPc.equals("pasar")) {
            if (partidoNuevo.getTienePelotaJugador() == 1) {
                partidoNuevo.setTienePelotaJugador(2);
            } else {
                partidoNuevo.setTienePelotaJugador(1);
            }
        }
    }


    @RequestMapping(value = "/partido-aro", method = RequestMethod.GET)
    public ModelAndView irAlAro(@RequestParam(required = true) Long idPartido, HttpServletRequest request) {
        if (verificar(request)){
            return new ModelAndView("redirect:login");
        };
        ModelMap modelo = new ModelMap();
        modelo.put("posicion", partidoNuevo.getPosicion());
        return new ModelAndView("partido-aro", modelo);
    }

    @RequestMapping(value = "/posicion", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView calcularPosicion(@RequestParam(required = true) Boolean resultado, Long idPartido) {
        Integer posicion = partidoNuevo.getPosicion();
        if (resultado) {
            if (posicion < 4) {
                posicion++;
            }
        } else {
            if (posicion > 1) {
                posicion--;
            }
        }
        partidoNuevo.setPosicion(posicion);

        return new ModelAndView("redirect:partido?idPartido=" + idPartido);
    }


    @RequestMapping("/partido-resultado")
    public ModelAndView irAlResultado(@RequestParam(required = true) Long idPartido, HttpServletRequest request) {
        if (verificar(request)){
            return new ModelAndView("redirect:login");
        };
        ModelMap modelo = new ModelMap();
        String mensaje;
        Long id = (Long) request.getSession().getAttribute("id");
        if (partidoNuevo.getPuntajeJugador() > partidoNuevo.getPuntajePc()) {
            mensaje = "GANASTE";
            servicioTienda.modificarDinero(500,id);
        } else {
            mensaje = "PERDISTE";
            servicioTienda.modificarDinero(100,id);
        }
        modelo.put("mensaje", mensaje);
        servicioPartido.guardarPuntajeFinal(idPartido, partidoNuevo);
        return new ModelAndView("partido-resultado", modelo);
    }

    @RequestMapping(value = "/tira", method = RequestMethod.POST)
    public ModelAndView procesarValorBarra(@RequestParam Integer resultadoBarra) {
        if (resultadoBarra >= 85) {
            if (partidoNuevo.getPosicion() == 4) {
                partidoNuevo.setPuntajeJugador(partidoNuevo.getPuntajeJugador() + 2);
            } else {
                partidoNuevo.setPuntajeJugador(partidoNuevo.getPuntajeJugador() + 3);
            }
        }
        partidoNuevo.setTengoLaPelota(false);
        partidoNuevo.setPosicion(4);
        Long idPartido = partidoNuevo.getIdPartido();
        return new ModelAndView("redirect:partido?idPartido=" + idPartido);
    }

    @RequestMapping(path = "/guardarPartido")
    public ModelAndView guardarPartido() {
        Long idPartido = partidoNuevo.getIdPartido();
        servicioPartido.guardarPartido(idPartido, partidoNuevo);
        ModelMap modelo = new ModelMap();
        modelo.put("idUltimoPartido", idPartido);
        return new ModelAndView("menuPrincipal", modelo);
    }

    @RequestMapping(value = "/historial", method = RequestMethod.GET)
    public ModelAndView mostrarHistorial(HttpServletRequest request) {
        if (verificar(request)){
            return new ModelAndView("redirect:login");
        };
        ModelMap modelo = new ModelMap();
        List<Partido> partidos = servicioPartido.listAll();
        modelo.put("partidos", partidos);
        return new ModelAndView("historial", modelo);
    }

    public PartidoDTO getPartidoNuevo() {
        return partidoNuevo;
    }

    public void setPartidoNuevo(PartidoDTO partidoNuevo) {
        this.partidoNuevo = partidoNuevo;
    }
}

