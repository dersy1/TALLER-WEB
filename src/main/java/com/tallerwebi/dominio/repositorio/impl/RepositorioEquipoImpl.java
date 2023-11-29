package com.tallerwebi.dominio.repositorio.impl;

import com.tallerwebi.dominio.modelo.Equipo;
import com.tallerwebi.dominio.repositorio.RepositorioEquipo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("repositorioEquipo")
public class RepositorioEquipoImpl  implements RepositorioEquipo{

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioEquipoImpl(SessionFactory sessionFactory){this.sessionFactory = sessionFactory;}


    @Override
    public Equipo buscar(Long idEquipo) {
        final Session session = sessionFactory.getCurrentSession();
        return(Equipo) session.createCriteria(Equipo.class).add(Restrictions.eq("id", idEquipo)).uniqueResult();
    }

    @Override
    public Equipo buscarPorNombre(String nombre) {
        final Session session = sessionFactory.getCurrentSession();
        return(Equipo) session.createCriteria(Equipo.class).add(Restrictions.eq("nombre", nombre)).uniqueResult();
    }

    @Override
    public void crear(Equipo equipo) {
        sessionFactory.getCurrentSession().save(equipo);
    }

    @Override
    public List<Equipo> listAll() {
        return sessionFactory.getCurrentSession().createCriteria(Equipo.class).list();
    }
}
