package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.modelo.ProductoTienda;
import com.tallerwebi.infraestructura.servicio.ServicioInventario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorInventario {

    private ServicioInventario servicioInventario;

    @Autowired
    public ControladorInventario(ServicioInventario servicioInventario) {
        this.servicioInventario = servicioInventario;
    }

    @RequestMapping("/inventario")
    public ModelAndView irAInventario() {
        ModelMap modelo = new ModelMap();
        modelo.put("inventario", servicioInventario.listAll());
        return new ModelAndView("inventario", modelo);
    }

}