package com.tallerwebi.dominio.repositorio.impl;


import com.tallerwebi.dominio.modelo.ProductoTienda;
import com.tallerwebi.dominio.repositorio.RepositorioTienda;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("repositorioTienda")
public class RepositorioTiendaImpl implements RepositorioTienda {
    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioTiendaImpl(SessionFactory sessionFactory){this.sessionFactory = sessionFactory;}

    @Override
    public ProductoTienda buscar(Long idProducto) {
        final Session session = sessionFactory.getCurrentSession();
        return(ProductoTienda) session.createCriteria(ProductoTienda.class).add(Restrictions.eq("idProducto", idProducto)).uniqueResult();
    }
    @Override
    public void crear(ProductoTienda productoTienda) {
        sessionFactory.getCurrentSession().save(productoTienda);
    }
    @Override
    public List<ProductoTienda> listAll() {
        return sessionFactory.getCurrentSession().createCriteria(ProductoTienda.class).list();
    }
    @Override
    public void eliminar(ProductoTienda productoTienda) {
        sessionFactory.getCurrentSession().delete(productoTienda);
    }
    @Override
    public void actualizar(ProductoTienda productoTienda) {
        sessionFactory.getCurrentSession().update(productoTienda);
    }
}