package com.tallerwebi.infraestructura.servicio.impl;

import com.tallerwebi.dominio.modelo.ProductoTienda;
import com.tallerwebi.dominio.repositorio.RepositorioTienda;
import com.tallerwebi.dominio.repositorio.RepositorioUsuario;
import com.tallerwebi.infraestructura.servicio.ServicioTienda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ServicioTiendaImpl implements ServicioTienda {
    @Autowired
    private RepositorioTienda repositorioTienda;
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioTiendaImpl(RepositorioTienda repositorioTienda, RepositorioUsuario repositorioUsuario){
        this.repositorioTienda = repositorioTienda;
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public ProductoTienda buscar(Long id) {
        return this.repositorioTienda.buscar(id);
    }

    @Override
    public void inicializarItemTienda(String imagen, String nombre, Integer precio) {
        ProductoTienda productoTienda =new ProductoTienda();
        productoTienda.setImagen(imagen);
        productoTienda.setNombre(nombre);
        productoTienda.setPrecio(precio);
        repositorioTienda.crear(productoTienda);
    }

    //    @Override
//    public void inicializarItemTienda() {
//        final String imagen="images/ITEM.png";
//        inicializarItemTienda(imagen,"PowerShoot",40);
//        inicializarItemTienda(imagen,"PowerPass",35);
//        inicializarItemTienda(imagen,"PowerDrible",35);
//        inicializarItemTienda(imagen,"GatoradeSteal",35);
//        inicializarItemTienda(imagen,"GatoradeIntercept",35);
//        inicializarItemTienda(imagen,"GatoradeBlock",40);
//    }
    @Override
    public void inicializarItemTienda() {
//    final String imagen="images/ITEM.png";
//    inicializarItemTienda(imagen,"PowerShoot",40);
//    inicializarItemTienda(imagen,"PowerPass",35);
//    inicializarItemTienda(imagen,"PowerDrible",35);
//    inicializarItemTienda(imagen,"GatoradeSteal",35);
//    inicializarItemTienda(imagen,"GatoradeIntercept",35);
//    inicializarItemTienda(imagen,"GatoradeBlock",40);
    }
    @Override
    public List<ProductoTienda> listAll() {
        return repositorioTienda.listAll();
    }

    @Override
    public Integer mostrarDinero() {
        return repositorioUsuario.mostrarDinero();
    }
    public void modificarDinero(Integer dinero, Long id){
        repositorioUsuario.modificarDinero(dinero, 0, id);
    }
}