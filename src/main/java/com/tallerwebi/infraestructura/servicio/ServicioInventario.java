package com.tallerwebi.infraestructura.servicio;

import com.tallerwebi.dominio.modelo.Inventario;
import com.tallerwebi.dominio.modelo.ProductoTienda;

import java.util.List;

public interface ServicioInventario {

    List<Inventario> listAll();

    void agregar(ProductoTienda producto, Integer dinero, Long id);
    void consumir(Inventario inventario);
    Inventario buscar(Long idProductoInventario);
    Inventario buscar(String nombreProducto);
}
