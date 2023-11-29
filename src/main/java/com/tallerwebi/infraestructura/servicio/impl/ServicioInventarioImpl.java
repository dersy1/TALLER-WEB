package com.tallerwebi.infraestructura.servicio.impl;

import com.tallerwebi.dominio.modelo.Inventario;
import com.tallerwebi.dominio.modelo.ProductoTienda;
import com.tallerwebi.dominio.repositorio.RepositorioInventario;
import com.tallerwebi.infraestructura.servicio.ServicioInventario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ServicioInventarioImpl implements ServicioInventario {

    @Autowired
    private RepositorioInventario repositorioInventario;

    @Autowired
    public ServicioInventarioImpl(RepositorioInventario repositorioInventario){
        this.repositorioInventario = repositorioInventario;

    }
    @Override
    public Inventario buscar(Long idProductoInventario){
        return repositorioInventario.buscar(idProductoInventario);
    }
    @Override
    public Inventario buscar(String nombre){
        return repositorioInventario.buscar(nombre);
    }

    @Override
    public List<Inventario> listAll(){
        return repositorioInventario.listAll();
    }

    @Override
    public void agregar(ProductoTienda producto, Integer dinero, Long id) {
        repositorioInventario.agregar(producto, dinero, id);
    }
    @Override
    public void consumir(Inventario inventario) {
        repositorioInventario.consumir(inventario);
    }
}