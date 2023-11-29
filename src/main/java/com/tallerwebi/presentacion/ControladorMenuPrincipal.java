package com.tallerwebi.presentacion;

import com.tallerwebi.infraestructura.servicio.ServicioEquipo;
import com.tallerwebi.infraestructura.servicio.ServicioTienda;
import com.tallerwebi.infraestructura.servicio.ServicioJugador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorMenuPrincipal {
    @Autowired
    private ServicioEquipo servicioEquipo;
    @Autowired
    private ServicioJugador servicioJugador;
    @Autowired
    private ServicioTienda servicioTienda;

    @RequestMapping("/menuPrincipal")
    public ModelAndView irAMenuPrincipal() {
        //servicioItemTienda.inicializarItemTienda();
        return new ModelAndView("menuPrincipal");
    }


}
