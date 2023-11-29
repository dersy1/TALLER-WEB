package com.tallerwebi.dominio.repositorio;
import com.tallerwebi.dominio.modelo.ProductoTienda;

import java.util.List;
public interface RepositorioTienda{
    ProductoTienda buscar(Long id);
    void crear(ProductoTienda productoTienda);
    void eliminar(ProductoTienda productoTienda);
    void actualizar(ProductoTienda productoTienda);
    List<ProductoTienda> listAll();
}