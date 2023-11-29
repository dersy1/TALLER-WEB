package com.tallerwebi.infraestructura.servicio;

import com.tallerwebi.dominio.modelo.ProductoTienda;

import java.util.List;

public interface ServicioTienda {
    ProductoTienda buscar(Long id);
    void inicializarItemTienda(String imagen, String nombre, Integer precio);
    void inicializarItemTienda();
    List<ProductoTienda> listAll();
    Integer mostrarDinero();
    void modificarDinero(Integer dinero, Long id);
}
