package com.tallerwebi.dominio.repositorio.impl;

import com.tallerwebi.dominio.modelo.Jugador;
import com.tallerwebi.dominio.repositorio.RepositorioJugador;
//import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("repositorioJugador")
public class RepositorioJugadorImpl implements RepositorioJugador {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioJugadorImpl(SessionFactory sessionFactory){this.sessionFactory = sessionFactory;}


    @Override
    public Jugador buscar(String nombre) {
        final Session session = sessionFactory.getCurrentSession();
        return(Jugador) session.createCriteria(Jugador.class).add(Restrictions.eq("nombre", nombre)).uniqueResult();
    }
    @Override
    public void guardar(Jugador jugador) {
        sessionFactory.getCurrentSession().save(jugador);
    }
}

